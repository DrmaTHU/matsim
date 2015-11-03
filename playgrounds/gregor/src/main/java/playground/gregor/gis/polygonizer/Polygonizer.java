/* *********************************************************************** *
 * project: org.matsim.*
 * Polygonizer.java
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

package playground.gregor.gis.polygonizer;

import org.matsim.core.utils.gis.ShapeFileReader;
import org.opengis.feature.simple.SimpleFeature;

import playground.gregor.sim2d_v3.helper.gisdebug.GisDebugger;

import com.vividsolutions.jts.geom.Geometry;

public class Polygonizer {
	
	public static void main(String args[]) {
		String input = "/Users/laemmel/devel/burgdorf/dxf/station-1_1.shp";
		ShapeFileReader reader = new ShapeFileReader();
		reader.readFileAndInitialize(input);
		Geometry res = null;
		for (SimpleFeature ft : reader.getFeatureSet()) {
			Geometry geo = (Geometry) ft.getDefaultGeometry();
			if (res == null) {
				res = geo;
			} else {
				res = res.union(geo);
			}
		}
		Geometry res2 = res.buffer(.05);
		
		Geometry e = res.getEnvelope();
		
		Geometry e2 = e.difference(res2);
		GisDebugger.addGeometry(e2);
		GisDebugger.dump("/Users/laemmel/devel/burgdorf2d2/raw_input/station-1_stage1.shp");
		
		System.out.println(res);
		
	}

}