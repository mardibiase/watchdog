package nl.tudelft.watchdog.eclipse.logic.ui.listeners;

import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchListener;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import nl.tudelft.watchdog.core.logic.ui.events.WatchDogEvent;
import nl.tudelft.watchdog.core.logic.ui.events.WatchDogEvent.EventType;
import nl.tudelft.watchdog.eclipse.logic.InitializationManager;
import nl.tudelft.watchdog.eclipse.logic.network.TransferManager;
import nl.tudelft.watchdog.eclipse.logic.ui.WatchDogEventManager;

/**
 * Sets up the listeners for eclipse UI events and registers the shutdown
 * listeners.
 */
public class WorkbenchListener {
	/** The serialization manager. */
	private TransferManager transferManager;

	/** The editorObservable. */
	private WatchDogEventManager watchDogEventManager;

	/**
	 * The window listener. An Eclipse window is the whole Eclipse application
	 * window.
	 */
	private WindowListener windowListener;

	private IWorkbench workbench;

	/** Constructor. */
	public WorkbenchListener(WatchDogEventManager userActionManager,
			TransferManager transferManager) {
		this.watchDogEventManager = userActionManager;
		this.transferManager = transferManager;
		this.workbench = PlatformUI.getWorkbench();
	}

	/**
	 * Adds listeners to Workbench including already opened windows and
	 * registers shutdown listeners.
	 */
	public void attachListeners() {
		watchDogEventManager
				.update(new WatchDogEvent(workbench, EventType.START_IDE));
		windowListener = new WindowListener(watchDogEventManager);
		workbench.addWindowListener(windowListener);
		addListenersToAlreadyOpenWindows();
		new JUnitListener(watchDogEventManager);
		new GeneralActivityListener(watchDogEventManager,
				workbench.getDisplay());
		addShutdownListeners();
	}

	/** The shutdown listeners, executed when Eclipse is shutdown. */
	private void addShutdownListeners() {
		workbench.addWorkbenchListener(new IWorkbenchListener() {

			private InitializationManager initializationManager;

			@Override
			public boolean preShutdown(final IWorkbench workbench,
					final boolean forced) {
				initializationManager = InitializationManager
						.getInstance();
				watchDogEventManager.update(
						new WatchDogEvent(workbench, EventType.END_IDE));
				initializationManager.getIntervalManager().closeAllIntervals();
				transferManager.sendItemsImmediately();
				return true;
			}

			@Override
			public void postShutdown(final IWorkbench workbench) {
				initializationManager.shutdown();
			}
		});
	}

	/**
	 * If windows are already open when the listener registration from WatchDog
	 * starts (e.g. due to saved Eclipse workspace state), add these listeners
	 * to already opened windows.
	 * 
	 * This is usually the single Eclipse application window.
	 */
	private void addListenersToAlreadyOpenWindows() {
		for (IWorkbenchWindow window : workbench.getWorkbenchWindows()) {
			windowListener.windowOpened(window);
		}
		IWorkbenchWindow activeWindow = workbench.getActiveWorkbenchWindow();
		windowListener.windowActivated(activeWindow);
	}

}