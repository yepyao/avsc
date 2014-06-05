package data;

public class Project {
	public String projectid; //project's unique identifier 
	public String teacher_acctid; //teacher's unique identifier (teacher that created a project)
	public String schoolid; //school's unique identifier (school where teacher works)
	public String school_ncesid; //public National Center for Ed Statistics id
	public double school_latitude;
	public double school_longitude;
	public String school_city;
	public String school_state;
	public String school_zip;
	public String school_metro;
	public String school_district;
	public String school_county;
	public boolean school_charter; //whether a public charter school or not (no private schools in the dataset)
	public boolean school_magnet; //whether a public magnet school or not
	public boolean school_year_round; //whether a public year round school or not
	public boolean school_nlns; //whether a public nlns school or not
	public boolean school_kipp; //whether a public kipp school or not
	public boolean school_charter_ready_promise; //whether a public ready promise school or not
	public String teacher_prefix; //teacher's gender
	public boolean teacher_teach_for_america; //Teach for America or not
	public boolean teacher_ny_teaching_fellow; //New York teaching fellow or not
	public String primary_focus_subject; //main subject for which project materials are intended
	public String primary_focus_area; //main subject area for which project materials are intended
	public String secondary_focus_subject; //secondary subject
	public String secondary_focus_area; //secondary subject area
	public String resource_type; //main type of resources requested by a project
	public String poverty_level; //school's poverty level. 
								//highest: 65%+ free of reduced lunch
								//high: 40-64%
								//moderate: 10-39%
								//low: 0-9%
	public String grade_level; //grade level for which project materials are intended
	public double fulfillment_labor_materials; //cost of fulfillment
	public double total_price_excluding_optional_support; //project cost excluding optional tip that donors give to DonorsChoose.org while funding a project 
	public double total_price_including_optional_support; //see above
	public int students_reached; //number of students impacted by a project (if funded)
	public boolean eligible_double_your_impact_match;// project was eligible for a 50% off offer by a corporate partner (logo appears on a project, like Starbucks or Disney)
	public boolean eligible_almost_home_match; //project was eligible for a $100 boost offer by a corporate partner
	public String date_posted; //data a project went live on the site
	
	public static Project render(String line){
		Project project = new Project();
		try{
			String[] arr = CSVFileUtil.fromCSVLine(line, 35);
			//render
			project.projectid = arr[0];
			project.teacher_acctid = arr[1];
			project.schoolid = arr[2];
			project.school_ncesid = arr[3];
			project.school_latitude = Helper.parseDouble(arr[4]);
			project.school_longitude = Helper.parseDouble(arr[5]);
			project.school_city = arr[6];
			project.school_state = arr[7];
			project.school_zip = arr[8];
			project.school_metro = arr[9];
			project.school_district = arr[10];
			project.school_county = arr[11];
			project.school_charter = Helper.parseBool(arr[12]);
			project.school_magnet = Helper.parseBool(arr[13]);
			project.school_year_round = Helper.parseBool(arr[14]);
			project.school_nlns = Helper.parseBool(arr[15]);
			project.school_kipp = Helper.parseBool(arr[16]);
			project.school_charter_ready_promise = Helper.parseBool(arr[17]);
			project.teacher_prefix = arr[18];
			project.teacher_teach_for_america = Helper.parseBool(arr[19]);
			project.teacher_ny_teaching_fellow = Helper.parseBool (arr[20]);
			project.primary_focus_subject = arr[21];
			project.primary_focus_area = arr[22];
			project.secondary_focus_subject = arr[23];
			project.secondary_focus_area = arr[24];
			project.resource_type = arr[25];
			project.poverty_level = arr[26];
			project.grade_level = arr[27];
			project.fulfillment_labor_materials = Helper.parseDouble('0'+arr[28]);
			project.total_price_excluding_optional_support = Helper.parseDouble(arr[29]);
			project.total_price_including_optional_support = Helper.parseDouble(arr[30]); 
			project.students_reached = Integer.parseInt('0'+arr[31]);
			project.eligible_double_your_impact_match = Helper.parseBool(arr[32]);
			project.eligible_almost_home_match = Helper.parseBool(arr[33]);
			project.date_posted = arr[34];
		}catch (Exception e){
			e.printStackTrace();
			System.err.println(line);
			System.exit(1);
		}
		return project;
	}
}
