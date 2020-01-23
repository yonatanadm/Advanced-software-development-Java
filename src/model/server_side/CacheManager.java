package server_side;


public interface CacheManager<Problem,Solution> 
{
	Solution searchSolution(Problem problem);
	void saveSolution(Problem problem,Solution solution);
	
}
