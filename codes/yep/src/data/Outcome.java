package data;

public class Outcome {
	public String origin;
	public String projectid;
	public boolean is_exciting; //ground truth of whether a project is exciting from business perspective
	public boolean at_least_1_teacher_referred_donor; //teacher referred = donor donated because teacher shared a link or publicized their page
	public boolean fully_funded; //project was successfully completed
	public boolean at_least_1_green_donation; //a green donation is a donation made with credit card, PayPal, Amazon or check
	public boolean great_chat; //project has a comment thread with greater than average unique comments
	public boolean three_or_more_non_teacher_referred_donors; //non-teacher referred is a donor that landed on the site by means other than a teacher referral link/page
	public boolean one_non_teacher_referred_donor_giving_100_plus; //see above
	public boolean donation_from_thoughtful_donor; //a curated list of ~15 donors that are power donors and picky choosers (we trust them selecting great projects)
	public double great_messages_proportion; //how great_chat is calculated. proportion of comments on the project page that are unique. If > avg (currently 62%) then great_chat = True
	public double teacher_referred_count; //number of donors that were teacher referred (see above)
	public double non_teacher_referred_count; //number of donors that were non-teacher referred
	
	public static Outcome render(String line){
		Outcome outcome = new Outcome();
		try{
			//outcome.origin = line; 
			String[] arr = CSVFileUtil.fromCSVLine(line, 12);
			//render
			outcome.projectid = arr[0];
			outcome.is_exciting = Helper.parseBool(arr[1]);
			outcome.at_least_1_teacher_referred_donor = Helper.parseBool(arr[2]);
			outcome.fully_funded = Helper.parseBool(arr[3]);
			outcome.at_least_1_green_donation = Helper.parseBool(arr[4]);
			outcome.great_chat = Helper.parseBool(arr[5]);
			outcome.three_or_more_non_teacher_referred_donors = Helper.parseBool(arr[6]);
			outcome.one_non_teacher_referred_donor_giving_100_plus = Helper.parseBool(arr[7]);
			outcome.donation_from_thoughtful_donor = Helper.parseBool(arr[8]);
			outcome.great_messages_proportion = Helper.parseDouble(arr[9]);
			outcome.teacher_referred_count = Helper.parseDouble(arr[10]);
			outcome.non_teacher_referred_count = Helper.parseDouble(arr[11]);
		}catch (Exception e){
			e.printStackTrace();
			System.err.println(line);
			System.exit(1);
		}
		return outcome;
	}
}
