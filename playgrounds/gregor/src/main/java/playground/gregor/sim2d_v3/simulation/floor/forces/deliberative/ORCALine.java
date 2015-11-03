/* *********************************************************************** *
 * project: org.matsim.*
 * ORCALineTMP.java
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2012 by the members listed in the COPYING,        *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */

package playground.gregor.sim2d_v3.simulation.floor.forces.deliberative;

public interface ORCALine {

//	public abstract void gisDump();

	public abstract boolean solutionSatisfyConstraint(float[] ret);

	public abstract boolean solutionSatisfiesConstraint(float x, float y);

	public abstract float getPointX();

	public abstract float getPointY();
	
	public abstract void setPointX(float x);
	
	public abstract void setPointY(float y);

	public abstract float getDirectionX();

	public abstract float getDirectionY();

}