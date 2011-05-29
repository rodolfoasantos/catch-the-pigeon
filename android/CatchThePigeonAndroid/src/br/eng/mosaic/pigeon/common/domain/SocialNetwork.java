package br.eng.mosaic.pigeon.common.domain;

public class SocialNetwork {

	private String id;
	private String name;

	public SocialNetwork(){
		this.id = "";
		this.name = "";
	}
	
	public SocialNetwork(String id, String name){
		this.id = id;
		this.name = name;
		User u = new User();
	}
	
	/**
	 * 
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		try {
			SocialNetwork sn = (SocialNetwork)obj;
			if((this.id == sn.id) && (this.name == sn.name))
				return true;
			return false;	
		} catch (Exception e) {
			return false;
		}
		
	}
}