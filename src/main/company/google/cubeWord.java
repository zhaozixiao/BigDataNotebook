public class Solution {

	public boolean ifCubeCanConstructWord(List<char[]> cubes, String word) {
		if (word==null||word.length()==0) return true;
		if (cubes==null||cubes.size()==0||cubes.size()<word.length()) return false;

		int csize = cubes.size();
		int wsize = word.length();
		int[] cubeMap = new int[csize];
		for (int j=0;j<cubes.size();j++) {
			for (char target:cubes.get(j)) {
				cubeMap[j] |= 1<<(target-'a');
			}
		}
		char [] chars = word.toCharArray();
		int [][] res = new int [wsize][math.pow(2,csize)]; // first digit: if visited, second digit: if works for future exploration
		return backtrack(0,cubeMap,res,wsize,0);
	}

	public boolean backtrack(int start, int[] cubeMap, int [][] res, String word, int layout) {
		if (start==word.length()) {
			return true;
		}
		if (res[start][layout] ==1) return false;
		for (int j=0;j<cubeMap.length;j++) {
			if ((cubeMap[j]>>(word.charAt(start)-'a') & 1 ==1) && (layout>>j&1==0)) {
				int newlayout = layout|(1<<j);
				boolean res= backtrack(start+1,cubeMap,res,word,newlayout);
				if (res) {
					return true;
				}
			}
		}
		res[start][layout]=1;
		return false;
	}
}