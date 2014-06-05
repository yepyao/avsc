package data;

public class Donation {
	public String donationid; //unique donation identifier
	public String projectid; //unique project identifier (project that received the donation)
	public String donor_acctid; //unique donor identifier (donor that made a donation)
	public String donor_city;
	public String donor_state;
	public String donor_zip;
	public boolean is_teacher_acct; //donor is also a teacher
	public String donation_timestamp;
	public double donation_to_project; //amount to project, excluding optional support (tip)
	public double donation_optional_support; //amount of optional support
	public double donation_total; //donated amount
	public String dollar_amount; //donated amount in US dollars
	public boolean donation_included_optional_support; //whether optional support (tip) was included for DonorsChoose.org
	public String payment_method; //what card/payment option was used
	public boolean payment_included_acct_credit; //whether a portion of a donation used account credits redemption
	public boolean payment_included_campaign_gift_card; //whether a portion of a donation included corporate sponsored giftcard
	public boolean payment_included_web_purchased_gift_card; //whether a portion of a donation included citizen purchased giftcard (ex: friend buy a giftcard for you)
	public boolean payment_was_promo_matched; //whether a donation was matched 1-1 with corporate funds
	public boolean via_giving_page; //donation given via a giving / campaign page (example: Mustaches for Kids)
	public boolean for_honoree; //donation made for an honoree
	public String donation_message; //donation comment/message. Used to calcualte great_chat
	
	public static Donation render(String line){
		Donation donation = new Donation();
		try{
			String[] arr = CSVFileUtil.fromCSVLine(line, 21);
			//render
			donation.donationid = arr[0];
			donation.projectid = arr[1];
			donation.donor_acctid = arr[2];
			donation.donor_city = arr[3];
			donation.donor_state = arr[4];
			donation.donor_zip = arr[5];
			donation.is_teacher_acct = Helper.parseBool(arr[6]);
			donation.donation_timestamp = arr[7];
			donation.donation_to_project = Helper.parseDouble(arr[8]);
			donation.donation_optional_support = Helper.parseDouble(arr[9]);
			donation.donation_total = Helper.parseDouble(arr[10]);
			donation.dollar_amount = arr[11];
			donation.donation_included_optional_support = Helper.parseBool(arr[12]);
			donation.payment_method = arr[13];
			donation.payment_included_acct_credit = Helper.parseBool(arr[14]);
			donation.payment_included_campaign_gift_card = Helper.parseBool(arr[15]);
			donation.payment_included_web_purchased_gift_card = Helper.parseBool(arr[16]);
			donation.payment_was_promo_matched = Helper.parseBool(arr[17]);
			donation.via_giving_page = Helper.parseBool(arr[18]);
			donation.for_honoree = Helper.parseBool(arr[19]);
			donation.donation_message = arr[20];
			
		}catch (Exception e){
			e.printStackTrace();
			System.err.println(line);
			System.exit(1);
		}
		return donation;
	}
}
