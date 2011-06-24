package ttp.metaheuristic.grasp;

import org.apache.log4j.Logger;

import ttp.constructionheuristics.IConstructionHeuristics;
import ttp.localsearch.neighborhood.ILocalSearch;
import ttp.metaheuristic.ISearch;
import ttp.metaheuristic.tabu.TabuSearch;
import ttp.model.TTPInstance;
import ttp.model.TTPSolution;

public class GRASP implements ISearch<TTPInstance, TTPSolution> {

	private ILocalSearch<TTPSolution> localSearch;

	private IConstructionHeuristics<TTPInstance, TTPSolution> constructionHeuristic;

	private int noTries = 10;

	private static Logger logger = Logger.getLogger(GRASP.class);

	public ILocalSearch<TTPSolution> getLocalSearch() {
		return localSearch;
	}

	public void setLocalSearch(ILocalSearch<TTPSolution> localSearch) {
		this.localSearch = localSearch;
	}

	public IConstructionHeuristics<TTPInstance, TTPSolution> getConstructionHeuristic() {
		return constructionHeuristic;
	}

	public void setConstructionHeuristic(
			IConstructionHeuristics<TTPInstance, TTPSolution> constructionHeuristic) {
		this.constructionHeuristic = constructionHeuristic;
	}

	public int getNoTries() {
		return noTries;
	}

	public void setNoTries(int noTries) {
		this.noTries = noTries;
	}

	@Override
	public TTPSolution doSearch(TTPInstance instance) {

		TTPSolution bestSolution = null;
		constructionHeuristic.setProblemInstance(instance);

		for (int i = 0; i < noTries; i++) {
			// get initial solution
			TTPSolution initialSolution = constructionHeuristic
					.getInitialSolution();

			// apply local search
			TTPSolution lsSolution = localSearch.doLocalSearch(initialSolution);

			logger.info("Iter: " + i + " Current Solution: " + lsSolution.getCost());

			if (bestSolution == null
					|| lsSolution.getCost() < bestSolution.getCost()) {
				bestSolution = lsSolution;
			}
		}

		return bestSolution;
	}

}
