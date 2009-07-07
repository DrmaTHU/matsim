/**
 *
 */
package playground.yu.analysis;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.matsim.core.api.experimental.population.PlanElement;
import org.matsim.core.gbl.Gbl;
import org.matsim.core.network.MatsimNetworkReader;
import org.matsim.core.network.NetworkLayer;
import org.matsim.core.population.ActivityImpl;
import org.matsim.core.population.MatsimPopulationReader;
import org.matsim.core.population.PersonImpl;
import org.matsim.core.population.PopulationImpl;
import org.matsim.core.population.PopulationImpl;
import org.matsim.core.utils.io.IOUtils;
import org.matsim.population.algorithms.AbstractPersonAlgorithm;

/**
 * @author yu
 * 
 */
public class ActTimeEstimator extends AbstractPersonAlgorithm {
	private static class ActTimeCounter {
		private int count = 0;
		private double sum = 0.0, min = 86400.0, max = 0.0;

		public double getMin() {
			return this.min;
		}

		public double getMax() {
			return this.max;
		}

		public void add(final double time) {
			this.sum += time;
			this.count++;
			if (time > this.max) {
				this.max = time;
			} else if (time < this.min) {
				this.min = time;
			}
		}

		public double getAvg() {
			return this.sum / this.count;
		}
	}

	private static class ActDurCounter extends ActTimeCounter {

	}

	private static class ActStartTimeCounter extends ActTimeCounter {

	}

	private static class ActEndTimeCounter extends ActTimeCounter {

	}

	@SuppressWarnings("deprecation")
	@Override
	public void run(final PersonImpl person) {
		for (PlanElement pe : person.getSelectedPlan().getPlanElements()) {
			if (pe instanceof ActivityImpl) {
				ActivityImpl a = (ActivityImpl) pe;
				String actType = a.getType();
				ActDurCounter adc = this.actDurs.get(actType);
				if (adc == null) {
					adc = new ActDurCounter();
					this.actDurs.put(actType, adc);
				}
				adc.add(a.getDuration());
				ActStartTimeCounter astc = this.actStarts.get(actType);
				if (astc == null) {
					astc = new ActStartTimeCounter();
					this.actStarts.put(actType, astc);
				}
				astc.add(a.getStartTime());
				ActEndTimeCounter aetc = this.actEnds.get(actType);
				if (aetc == null) {
					aetc = new ActEndTimeCounter();
					this.actEnds.put(actType, aetc);
				}
				aetc.add(a.getEndTime());
			}
		}
	}

	private final Map<String, ActDurCounter> actDurs = new HashMap<String, ActDurCounter>();
	private final Map<String, ActStartTimeCounter> actStarts = new HashMap<String, ActStartTimeCounter>();
	private final Map<String, ActEndTimeCounter> actEnds = new HashMap<String, ActEndTimeCounter>();

	public void write(final String outputFilename) {
		try {
			BufferedWriter writer = IOUtils.getBufferedWriter(outputFilename);
			writer
					.write("actType\tactDur\tavg.\tmin\tmax\tactStart\tavg.\tmin\tmax\tactEnd\tavg.\tmin\tmax\n");
			for (String actType : this.actDurs.keySet()) {
				ActDurCounter adc = this.actDurs.get(actType);
				ActStartTimeCounter astc = this.actStarts.get(actType);
				ActEndTimeCounter aetc = this.actEnds.get(actType);
				writer.write(actType + "\tactDur\t" + adc.getAvg() + "\t"
						+ adc.getMin() + "\t" + adc.getMax() + "\tactStart\t"
						+ astc.getAvg() + "\t" + astc.getMin() + "\t"
						+ astc.getMax() + "\tactEnd\t" + aetc.getAvg() + "\t"
						+ aetc.getMin() + "\t" + aetc.getMax() + "\n");
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		Gbl.startMeasurement();

		final String netFilename = "input/Toronto/toronto.xml";
		final String plansFilename = "input/Toronto/fout_chains210.xml.gz";
		final String outputFilename = "output/Toronto/fout_chains210_actDur.txt";

//		Gbl.createConfig(null);

		NetworkLayer network = new NetworkLayer();
		new MatsimNetworkReader(network).readFile(netFilename);

		PopulationImpl population = new PopulationImpl();

		ActTimeEstimator ade = new ActTimeEstimator();

		System.out.println("-->reading plansfile: " + plansFilename);
		new MatsimPopulationReader(population, network).readFile(plansFilename);

		ade.run(population);

		ade.write(outputFilename);

		System.out.println("--> Done!");
		Gbl.printElapsedTime();
		System.exit(0);
	}
}
