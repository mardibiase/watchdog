package nl.tudelft.watchdog.eclipse.ui.wizards;

/**
 * Possible finishing page in the wizard. If the user exists on the server, or
 * the server is not reachable, the user can exit here.
 */
public abstract class RegistrationEndingPageBase extends FinishableWizardPage {

	/**
	 * The user id (either as retrieved from the previous page or as freshly
	 * accepted from the server).
	 */
	protected String id = "";

	/** Whether the registration was successful. */
	protected boolean successfulRegistration = false;

	/** The string to be shown as the window title. */
	protected String windowTitle;

	/** The message title to be shown above the messageBody. */
	protected String messageTitle;

	/** The message to be displayed. */
	protected String messageBody;

	/** Constructor. */
	protected RegistrationEndingPageBase(String pageName, int pageNumber) {
		super(pageName, pageNumber);
	}

	/** Template method that performs the registration. */
	abstract protected void makeRegistration();

	@Override
	public boolean canFinish() {
		return successfulRegistration;
	}

}
