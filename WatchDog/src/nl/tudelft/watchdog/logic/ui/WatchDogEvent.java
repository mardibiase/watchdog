package nl.tudelft.watchdog.logic.ui;

import java.util.EventObject;

/** Any event transferred by WatchDog. */
public class WatchDogEvent extends EventObject {

	/** Serial version. */
	private static final long serialVersionUID = 1L;

	/** Constructor. */
	public WatchDogEvent(Object source, EventType type) {
		super(source);
		this.type = type;
	}

	/** The type of the event. */
	private EventType type;

	/** The different type of events. */
	@SuppressWarnings("javadoc")
	public enum EventType {
		ACTIVE_FOCUS, END_FOCUS, EDIT, CARET_MOVED, PAINT,

		ACTIVE_WINDOW, END_WINDOW, START_ECLIPSE, END_ECLIPSE,

		START_DEBUG_PERSPECTIVE, START_JAVA_PERSPECTIVE, START_UNKNOWN_PERSPECTIVE, JUNIT,

		ACTIVITY, USER_INACTIVITY, EDITOR_INACTIVITY, WRITING_INACTIVITY
	}

	/** @return the {@link EventType} of this event. */
	public EventType getType() {
		return type;
	}

}