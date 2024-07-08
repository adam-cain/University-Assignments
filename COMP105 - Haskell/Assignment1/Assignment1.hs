-- Do not alter the following line
module Assignment1 (char_to_int, repeat_char, decode, int_to_char, length_char, drop_char, encode, complex_encode, complex_decode) where


-- Part A

char_to_int :: Char -> Integer
char_to_int '0' = 0
char_to_int '1' = 1
char_to_int '2' = 2
char_to_int '3' = 3
char_to_int '4' = 4
char_to_int '5' = 5
char_to_int '6' = 6
char_to_int '7' = 7
char_to_int '8' = 8
char_to_int '9' = 9

repeat_char :: Char -> Integer -> String
repeat_char c 0 = []
repeat_char c n = c : repeat_char c (n-1)

decode :: String -> String
decode [] = []
decode (x:y:xs) = (repeat_char x (char_to_int y)) ++ decode xs


-- Part B

int_to_char :: Integer -> Char
int_to_char 0 = '0'
int_to_char 1 = '1'
int_to_char 2 = '2'
int_to_char 3 = '3'
int_to_char 4 = '4'
int_to_char 5 = '5'
int_to_char 6 = '6'
int_to_char 7 = '7'
int_to_char 8 = '8'
int_to_char 9 = '9'

length_char :: Char -> String -> Integer
length_char c [] = 0
length_char c (x:xs)
 |c == x = 1 + length_char c xs
 |otherwise = length_char c []
 
drop_char :: Char -> String -> String
drop_char c [] = []
drop_char c (x:xs)
 |x == c = drop_char c xs
 |otherwise = (x:xs)

encode :: String -> String
encode [] = []
encode s = s_head : int_to_char(length_char s_head s) : encode (drop_char s_head s)
 where s_head = head s


-- Part C

int_to_str :: Integer -> String
int_to_str 0 = []
int_to_str int = int_to_str (div(int - ones) 10) ++ (int_to_char(ones):[])
 where ones = mod int 10

complex_encode :: String -> String
complex_encode [] = []
complex_encode s
 |(length_char s_head s) > 1 = s_head : int_to_str(length_char s_head s) ++ complex_encode (drop_char s_head s)
 |otherwise = s_head : complex_encode(tail s)
 where s_head = head s

is_int :: Char -> Bool
is_int i = elem i ['0'..'9']

str_to_int :: String -> Integer
str_to_int [] = 0
str_to_int (x:xs) = ((char_to_int x)*(10^((length(x:xs))-1))) + (str_to_int xs)

find_int_elements :: String -> String
find_int_elements [] = []
find_int_elements (x:xs)
 |is_int x = x : find_int_elements xs
 |otherwise = []

drop_int :: String -> String
drop_int [] = []
drop_int (x:xs)
 |is_int x = drop_int(xs)
 |otherwise = x:xs

complex_decode :: String -> String
complex_decode [] = []
complex_decode [x] = x:[]
complex_decode (x:y:xs)
 |is_int y = repeat_char x (str_to_int(find_int_elements (y:xs))) ++ (complex_decode (drop_int (y:xs)))
 |otherwise = x : complex_decode (y:xs)









