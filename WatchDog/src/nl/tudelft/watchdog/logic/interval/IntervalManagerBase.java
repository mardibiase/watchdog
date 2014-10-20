package nl.tudelft.watchdog.logic.interval;

import java.util.ArrayList;
import java.util.List;

import nl.tudelft.watchdog.logic.interval.intervaltypes.IntervalBase;
import nl.tudelft.watchdog.logic.interval.intervaltypes.IntervalType;

/**
 * Base class for managing intervals. Provides basic functionality for getting
 * certain types of intervals.
 */
public class IntervalManagerBase {

	/** A list of the managed intervals. */
	protected List<IntervalBase> intervals = new ArrayList<IntervalBase>();

	/**
	 * @return Returns an interval of the given class, if there is any such
	 *         open. If not, returns null.
	 */
	@SuppressWarnings("unchecked")
	public <T extends IntervalBase> T getIntervalOfClass(Class<T> clazz) {
		for (IntervalBase interval : intervals) {
			if (clazz.isAssignableFrom(interval.getClass())
					&& !interval.isClosed()) {
				return (T) interval;
			}
		}
		return null;
	}

	/**
	 * @return An interval of the specified type, or <code>null</code> if no
	 *         such interval is currently open.
	 */
	public IntervalBase getIntervalOfType(IntervalType type) {
		for (IntervalBase interval : intervals) {
			if (interval.getType() == type && !interval.isClosed()) {
				return interval;
			}
		}
		return null;
	}
}