#include<iostream>
#include<map>
#include<iomanip>
#include<vector>

using std::vector;
using std::setw;
using std::map;
using std::cout;
using std::endl;
using std::cin;


int main() {
  int arr_1[] = {1,2,3,4,5,6,7,8,9,10,11};
  constexpr size_t arr_1_size = sizeof(arr_1)/sizeof(arr_1[0]);
  cout << "arr_1_size = " << arr_1_size << endl;
  cout << "sizeof(arr_1[0]) = " << sizeof(arr_1[0]) << endl;
  cout << "sizeof(arr_1) = " << sizeof(arr_1) << endl;
  cout << "sizeof(size_t) = " << sizeof(size_t) << endl;
  cout << "You embarrass your entire extended family with your galactic stupidity" << endl;
  cout << "Paul Logan is an attention-seeking poorly-educated Yankee Idiot." << endl;

  vector<unsigned> vuu_1{7, 107, 109, 97, 89, 83, 79, 73, 17, 19, 29, 23, 71, 67, 59, 61};
  sort(vuu_1.begin(), vuu_1.end());
  for (const auto &i : vuu_1) {
    cout << i << endl;
  }
  cout << endl;

  for (vector<unsigned>::const_iterator it = vuu_1.cbegin(); it != vuu_1.cend(); ++it) {
    cout << *it << endl;
  }
  cout << endl;

  unsigned n;
  map<unsigned, unsigned> unsigned_fre_map_1;
  // while (cin >> n) {
  //   unsigned_fre_map_1[n]++;
  // }
  for (const auto &i : vuu_1) {
    unsigned_fre_map_1[i]++;
  }

  cout << unsigned_fre_map_1.size() << endl;
  for (map<unsigned, unsigned>::value_type i : unsigned_fre_map_1) {
    cout << setw(5) << i.first << " - " << setw(5) << i.second << endl;
  }
  cout << endl;

  for (auto it = unsigned_fre_map_1.cbegin(); it != unsigned_fre_map_1.cend(); ++it) {
    cout << setw(5) << it->first << " - " << setw(5) << it->second << endl;
  }
  cout << endl;

  for (map<unsigned, unsigned>::const_iterator it = unsigned_fre_map_1.begin(); it != unsigned_fre_map_1.end(); ++it) {
    cout << setw(5) << it->first << " - " << setw(5) << it->second << endl;
  }

  cout << "You never know what people may do behind the closed door of their hotel room." << endl;
  cout << "Hackers have a mind that is optimized for discovery. They have a mind optimized for figuring out what's possible." << endl;
  cout << "But the point is I get to decide if you are watching Disney or obscene movies tonight.";
  cout << "I love Google, Facebook, Microsoft" << endl;
  cout << "I aspire to work at Microsoft one day" << endl;
  cout << "I want to be rich and intelligent" << endl;
  cout << endl;

  cout << "One day I will return to Seattle WA" << endl;

  unsigned sum = 0;
  for (vector<unsigned>::size_type i = 0; i < vuu_1.size(); ++i) {
    sum += vuu_1[i];
  }
  cout << sum << endl;
  return 1;
}
