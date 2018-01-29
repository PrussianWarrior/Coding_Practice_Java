#include<iostream>
using std::cout;
using std::endl;

int main() {
  int arr_1[] = {1,2,3,4,5,6,7,8,9,10,11};
  constexpr size_t arr_1_size = sizeof(arr_1)/sizeof(arr_1[0]);
  cout << "arr_1_size = " << arr_1_size << endl;
  cout << "sizeof(arr_1[0]) = " << sizeof(arr_1[0]) << endl;
  cout << "sizeof(arr_1) = " << sizeof(arr_1) << endl;
  cout << "sizeof(size_t) = " << sizeof(size_t) << endl;
  cout << "You embarrass your entire extended family with your galactic stupidity" << endl;
  return 1;
}
