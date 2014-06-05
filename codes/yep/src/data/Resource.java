package data;

public class Resource {
	public String resourceid; //unique resource id
	public String projectid; //project id that requested resources for a classroom
	public int vendorid; //vendor id that supplies resources to a project
	public String vendor_name;
	public String project_resource_type; //type of resource
	public String item_name; //resource name (ex: ipad 32 GB)
	public String item_number; //resource item identifier 
	public double item_unit_price; //unit price of the resource
	public double item_quantity; //number of a specific item requested by a teacher
	
	public static Resource render(String line){
		Resource resource = new Resource();
		try{
			String[] arr = CSVFileUtil.fromCSVLine(line, 9);
			//render
			resource.resourceid = arr[0];
			resource.projectid = arr[1];
			resource.vendorid = Helper.parseInt(arr[2]);
			resource.vendor_name = arr[3];
			resource.project_resource_type = arr[4];
			resource.item_name = arr[5];
			resource.item_number = arr[6];
			resource.item_unit_price = Helper.parseDouble(arr[7]);
			resource.item_quantity = Helper.parseDouble(arr[8]);
		}catch (Exception e){
			e.printStackTrace();
			System.err.println(line);
			System.exit(1);
		}
		return resource;
	}
}
