-- Do not alter the following line
module Assignment2 (transaction_to_string, trade_report_list, stock_test, get_trades, trade_report, update_money, profit, profit_report, complex_profit_report) where


type Transaction = (Char, Int, Int, String, Int) 
--action unit price symbol day

test_log :: [Transaction]
test_log = [('B', 100, 1104,  "VTI",  1),
            ('B', 200,   36, "ONEQ",  3),
            ('B',  50, 1223,  "VTI",  5),
            ('S', 150, 1240,  "VTI",  9),
            ('B', 100,  229, "IWRD", 10),
            ('S', 200,   32, "ONEQ", 11), 
            ('S', 100,  210, "IWRD", 12)
            ]


-- Part A


transaction_to_string :: Transaction -> String
transaction_to_string (action, units, price, stock, day) = (if (action == 'B') then "Bought " else "Sold ") ++ show(units) ++" units of " ++ stock ++ " for " ++ show(price) ++ " pounds each on day " ++ show(day)


trade_report_list :: [Transaction] -> [String]
trade_report_list x = map transaction_to_string x


stock_test :: String -> Transaction -> Bool
stock_test stock (_, _, _, x, _) = if (stock == x) then True else False


get_trades :: String -> [Transaction] -> [Transaction]
get_trades stock t = filter (stock_test stock) t


trade_report :: String -> [Transaction] -> String
trade_report stock t = unlines(map transaction_to_string (get_trades stock t))


-- Part B
--action unit price symbol day

update_money :: Transaction -> Int -> Int
update_money (a, u, p, _, _) amount = if (a == 'B') then amount - (u * p) else amount + (u * p)


profit :: [Transaction] -> String -> Int
profit t stock = foldr (update_money)0 (get_trades stock t)



profit_report :: [String] -> [Transaction] -> String
profit_report [] _ = []
profit_report (x:xs) t = x ++ ": " ++ show(profit t x) ++"\n" ++ profit_report xs t


-- Part C
test_str_log = "BUY 100 VTI 1\nBUY 200 ONEQ 3\nBUY 50 VTI 5\nSELL 150 VTI 9\nBUY 100 IWRD 10\nSELL 200 ONEQ 11\nSELL 100 IWRD 12\n"

type TransactionLog = (Char, Int, String, Int) 
type Prices = [(String, [Int])]

test_prices :: Prices
test_prices = [
                ("VTI", [1689, 1785, 1772, 1765, 1739, 1725, 1615, 1683, 1655, 1725, 1703, 1726, 1725, 1742, 1707, 1688, 1697, 1688, 1675]),
                ("ONEQ", [201, 203, 199, 199, 193, 189, 189, 183, 185, 190, 186, 182, 186, 182, 182, 186, 183, 179, 178]),
                ("IWRD", [207, 211, 213, 221, 221, 222, 221, 218, 226, 234, 229, 229, 228, 222, 218, 223, 222, 218, 214])
              ]

-- Turns a log(test_str_log) to translog tuple list easily understandable
log_to_transaction :: String -> [TransactionLog]
log_to_transaction log = to_trans (words log)

-- Secondary function of log_to_transaction ^^^
to_trans :: [String] -> [TransactionLog]
to_trans [] = []
to_trans (act:unit:sym:day:xs) = [(act!!0, read(unit)::Int ,sym ,read(day)::Int)] ++ to_trans(xs)

-- Finds a list of a particular stocks prices(test_prices) through the days
find_prices :: String -> (String,[Int]) -> Bool
find_prices stock price = (fst price) == stock
get_prices :: String -> Prices -> (String, [Int])
get_prices stock price = (filter (find_prices stock) price)!!0

-- Concates the log and prices for that day
concate_transaction :: Prices -> TransactionLog -> (Char, Int, Int, String, Int)
concate_transaction price (act,units,stock,day) = (act,units,(snd(get_prices stock price)!!(day-1)),stock,day)

--Gets a list of all the stock symbols
get_symbols :: Prices -> [String]
get_symbols [] = []
get_symbols price = fst(head price) : get_symbols (tail price)

complex_profit_report :: String -> Prices -> String
complex_profit_report log prices = profit_report (get_symbols(prices)) (map (concate_transaction prices) (log_to_transaction log))

