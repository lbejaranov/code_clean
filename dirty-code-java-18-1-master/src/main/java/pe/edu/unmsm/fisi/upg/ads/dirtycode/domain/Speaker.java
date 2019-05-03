package pe.edu.unmsm.fisi.upg.ads.dirtycode.domain;

import java.util.Arrays;
import java.util.List;

import pe.edu.unmsm.fisi.upg.ads.dirtycode.exceptions.NoSessionsApprovedException;
import pe.edu.unmsm.fisi.upg.ads.dirtycode.exceptions.SpeakerDoesntMeetRequirementsException;

public class Speaker {
	private String firstName;
	private String lastName;
	private String email;
	private int experience;
	private boolean hasBlog;
	private String blogURL;
	private WebBrowser browser;
	private List<String> certifications;
	private String employer;
	private int registrationFee;
	private List<Session> sessions;
        
        static int min_years_experience = 10;
	static int min_number_certifications = 3;
	static int min_version_browser = 9;

	public Integer register(IRepository repository) throws Exception {
		Integer speakerId = null;
		boolean isGood = false;
		boolean isExperienceApproved = false;
		boolean isStandarApproved = false;
		boolean isApproved = false;		

		String[] technologies  = new String[] { "Cobol", "Punch Cards", "Commodore", "VBScript" };		
		List<String> domains = Arrays.asList("aol.com", "hotmail.com", "prodigy.com", "compuserve.com");
		List<String> employers = Arrays.asList("Pluralsight", "Microsoft", "Google", "Fog Creek Software", "37Signals", "Telerik");
		
		if (this.firstName.isEmpty()) throw new IllegalArgumentException("First Name is required");
		if (this.lastName.isEmpty()) throw new IllegalArgumentException("Last name is required.");
		if (this.email.isEmpty()) throw new IllegalArgumentException("Email is required.");
		if (this.sessions.size() == 0) throw new IllegalArgumentException("Can't register speaker with no sessions to present.");
			
		String[] splitted = this.email.split("@");
		String emailDomain = splitted[splitted.length - 1];
                
		isExperienceApproved = ((this.experience > min_years_experience || this.hasBlog || this.certifications.size() > min_number_certifications || employers.contains(this.employer)));
		isStandarApproved = (!domains.contains(emailDomain) && (!(browser.getName() == WebBrowser.BrowserName.InternetExplorer && browser.getMajorVersion() < min_version_browser)));
		
		isGood = isExperienceApproved || isStandarApproved;
					
		if(!isGood) throw new SpeakerDoesntMeetRequirementsException("Speaker doesn't meet our abitrary and capricious standards.");		
		
		for (Session session : sessions) {
			for (String technology : technologies) {				
				isApproved = !session.getTitle().contains(technology) && !session.getDescription().contains(technology);
				session.setApproved(isApproved);				
				if (!isApproved) break;						
			}			
		}		
		if (!isApproved) throw new NoSessionsApprovedException("No sessions approved.");
		
                if (this.experience <= 1) {
                        this.registrationFee = 500;
                }
                else if (experience >= 2 && experience <= 3) {
                        this.registrationFee = 250;
                }
                else if (experience >= 4 && experience <= 5) {
                        this.registrationFee = 100;
                }
                else if (experience >= 6 && experience <= 9) {
                        this.registrationFee = 50;
                }
                else {
                        this.registrationFee = 0;
                }		
		speakerId = repository.saveSpeaker(this);			
		return speakerId;
	}

	public List<Session> getSessions() {
		return sessions;
	}

	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int exp) {
		this.experience = exp;
	}

	public boolean isHasBlog() {
		return hasBlog;
	}

	public void setHasBlog(boolean hasBlog) {
		this.hasBlog = hasBlog;
	}

	public String getBlogURL() {
		return blogURL;
	}

	public void setBlogURL(String blogURL) {
		this.blogURL = blogURL;
	}

	public WebBrowser getBrowser() {
		return browser;
	}

	public void setBrowser(WebBrowser browser) {
		this.browser = browser;
	}

	public List<String> getCertifications() {
		return certifications;
	}

	public void setCertifications(List<String> certifications) {
		this.certifications = certifications;
	}

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	public int getRegistrationFee() {
		return registrationFee;
	}

	public void setRegistrationFee(int registrationFee) {
		this.registrationFee = registrationFee;
	}
}