package data;

public class Essay {
	public String projectid; //unique project identifier
	public String teacher_acctid; //teacher id that created a project
	public String title; //title of the project
	public String short_description; //description of a project
	public String need_statement; //need statement of a project
	public String essay; //complete project essay
	
	public static Essay render(String line){
		Essay essay = new Essay();
		try{
			String[] arr = CSVFileUtil.fromCSVLine(line, 6);
			//render
			essay.projectid = arr[0];
			essay.teacher_acctid = arr[1];
			essay.title = arr[2];
			essay.short_description = arr[3];
			essay.need_statement = arr[4];
			essay.essay = arr[5];
			//System.out.println(arr[5]);
		}catch (Exception e){
			e.printStackTrace();
			System.err.println(line);
			System.exit(1);
		}
		return essay;
	}
}
