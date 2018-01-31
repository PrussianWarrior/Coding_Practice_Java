#include<iostream>
#include<string>
#include<vector>

using std::vector;
using std::string;
using std::cout;
using std::cin;
using std::endl;
using std::begin;
using std::end;

int main() {
  cout << sizeof(int) << endl;
  cout << sizeof(unsigned) << endl;
  cout << sizeof(char) << endl;
  cout << sizeof(wchar_t) << endl;
  cout << sizeof(char16_t) << endl;
  cout << sizeof(char32_t) << endl;
  cout << sizeof(string) << endl;
  cout << sizeof(long) << endl;
  cout << sizeof(long long) << endl;
  cout << sizeof(unsigned long long) << endl;
  cout << sizeof(size_t) << endl;

  unsigned ui_arr_1[] = {2,3,5,7,11,13,17,19,23};
  cout << sizeof(ui_arr_1)/sizeof(ui_arr_1[0]) << " " << end(ui_arr_1) - begin(ui_arr_1) << endl;

  vector<unsigned> vu_1{29,31,37,41,43,47,53,59};
  vu_1.insert(vu_1.end(), begin(ui_arr_1), begin(ui_arr_1) + 5);
  for (auto it = vu_1.cbegin(); it != vu_1.cend(); ++it) {
    cout << *it << " ";
  }
  cout << endl;

  for (vector<unsigned>::size_type i = 0; i < vu_1.size(); ++i) {
    cout << vu_1[i] << " ";
  }
  cout << endl;

  for (vector<unsigned>::const_iterator it = vu_1.cbegin(); it != vu_1.cend(); ++it) {
    cout << *it << " ";
  }
  cout << endl;
  return 1;
}