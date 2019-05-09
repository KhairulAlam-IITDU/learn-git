package samsung;

public class Permutation {

	public static void main(String[] args) {
		
		int[] arr = {0, 1, 2, 3};
		
		permutate(arr, 0, arr.length-1);

	}

	private static void permutate(int[] arr, int l, int r) {
		
		if(l == r) {
			for(int num : arr){
				System.out.print(num + ", ");
			}
			System.out.println();
		}
		else {
			
			for (int i = l; i <= r; i++) {
				arr = swap(arr, l, i);
				permutate(arr, l+1, r);
				arr = swap(arr, l, i);
			}
		}
		
	}

	private static int[] swap(int[] arr, int l, int i) {
		
		int temp = arr[l];
		arr[l] = arr[i];
		arr[i] = temp;
		return arr;
	}

}
