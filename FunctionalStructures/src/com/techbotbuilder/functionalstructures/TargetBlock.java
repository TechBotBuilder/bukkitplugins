package com.techbotbuilder.functionalstructures;

import org.bukkit.Material;
import org.bukkit.util.Vector;

public class TargetBlock {
	private Vector v;
	private Material m;
	public TargetBlock(Vector v, Material m){
		this.v = v;
		this.m = m;
	}
	public void setType(Material m){
		this.m = m;
	}
	public void setDisplacement(Vector v){
		this.v = v;
	}
	public Material getType(){
		return m;
	}
	public Vector getDisplacement(){
		return v;
	}
}