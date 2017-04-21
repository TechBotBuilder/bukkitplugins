package com.techbotbuilder.functionalstructures;

import org.bukkit.Material;
import org.bukkit.util.Vector;

public class TargetBlock {
	private Vector v;
	private Material m;
	TargetBlock(Vector v, Material m){
		this.v = v;
		this.m = m;
	}
	void setType(Material m){
		this.m = m;
	}
	void setDisplacement(Vector v){
		this.v = v;
	}
	Material getType(){
		return m;
	}
	Vector getDisplacement(){
		return v;
	}
}