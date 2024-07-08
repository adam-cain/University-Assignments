module Main (get_maze, print_maze, is_wall, place_player, move, can_move, game_loop, get_path, main) where 

import System.Environment
import System.IO

maze_path = "C:\\Users\\03ada\\Desktop\\Assignment3\\maze3.txt"

-- Useful code from Lecture 25
-- You may use this freely in your solutions

get :: [String] -> Int -> Int -> Char
get maze x y = (maze !! y) !! x 

modify_list :: [a] -> Int -> a -> [a]
modify_list list pos new =
    let
        before = take  pos    list
        after  = drop (pos+1) list
    in
        before ++ [new] ++ after

set :: [String] -> Int -> Int -> Char -> [String]
set maze x y char = 
    let
        line = maze !! y
        new_line = modify_list line x char
        new_maze = modify_list maze y new_line
    in
        new_maze

---- Part A

-- Question 1

get_maze :: String -> IO [String]
get_maze path = do 
 file <- readFile path
 let formatted = lines file
 return formatted 
-- Question 2

print_maze :: [String] -> IO ()
print_maze maze = putStrLn (unlines maze)

-- Question 3

is_wall :: [String] -> (Int, Int) -> Bool
is_wall maze (x,y) = if (get maze x y) == '#' then True else False  

-- Question 4

place_player :: [String] -> (Int, Int) -> [String]
place_player maze (x,y) = set maze x y '@'


---- Part B

-- Question 5

move :: (Int, Int) -> Char -> (Int, Int)
move (x,y) 'w' = (x,y-1)
move (x,y) 's' = (x,y+1)
move (x,y) 'a' = (x-1,y)
move (x,y) 'd' = (x+1,y)
move (x,y) _ = (x,y) 

-- Question 6

can_move :: [String] -> (Int, Int) -> Char -> Bool
can_move maze (x,y) key = not (is_wall maze (move (x,y) key))

-- Question 7

-- maze, current player location
game_loop :: [String] -> (Int, Int) -> IO ()
game_loop maze (x,y) = do
 print_maze (place_player maze (x,y))
 inp <- getLine
 if (can_move maze (x,y) (inp!!0)) 
 then game_loop maze (move (x,y) (inp!!0))
 else game_loop maze (x,y)
  

---- Part C

-- Question 8
-- maze never loops as has no cycles
-- max of 4 moves per move

--x:xs is a list of new valid moves that have been found
--For each one of these they will be added to the front of the Fontier
add_frontier :: [(Int,Int)]-> [(Int,Int)] -> [[(Int,Int)]]
add_frontier [] _ = []
add_frontier (x:xs) frontier = [x : frontier] ++ add_frontier xs frontier

--Checks for every move each axis +1/-1 that it is not a wall or already in the frontier
valid_moves :: [String] -> (Int,Int) -> [(Int,Int)] -> [(Int,Int)]
valid_moves maze (x,y) frontier =
 next_cell 1 0 ++ next_cell (-1) 0 ++ next_cell 0 1 ++ next_cell 0 (-1)
 where next_cell i j = if not(is_wall maze (x+i,y+j)) && not(elem (x+i,y+j) frontier) then [(x+i,y+j)] else []

bfs :: [String] -> (Int,Int) -> [[(Int,Int)]] -> [(Int,Int)]
bfs _ _ [] = []
bfs maze target (qhead:qtail) = if (head qhead) == target 
 then qhead 
 else bfs maze target (qtail ++ (add_frontier (valid_moves maze (head qhead) qhead) qhead))

--Just intilises into proper types
get_path :: [String] -> (Int, Int) -> (Int, Int) -> [(Int, Int)]
get_path maze (m,n) (x,y) = bfs maze (m,n) [[(x,y)]]

-- Question 9
map_maze maze [] = print_maze maze
map_maze maze (x:xs) = map_maze (set maze (fst(x)) (snd(x)) '.') xs

main :: IO ()
main = do
 file_path <- getLine
 maze <- get_maze file_path
 let path = get_path maze (1,1) (length(maze!!0)-2,(length maze)-2)
 map_maze maze path
