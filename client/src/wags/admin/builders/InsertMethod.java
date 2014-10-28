package wags.admin.builders;

public enum InsertMethod {
	BY_VALUE ("byvalue"), 
	BY_VALUE_AND_LOC ("byvalueandlocation"), 
	BY_VALUE_LOC_COLOR ("byvaluelocationcolor");
	
	private String key;
	
	InsertMethod(String key){
		this.key = key;
	}
	
	@Override
	public String toString() {
		return key;
	}
}
