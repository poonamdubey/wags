% IsOdd Prolog Microlab
%
% Determines whether or not a number is odd
%
% Write a rule to determine whether or not a number is odd:
% Rule: 'odd(N)'
%   - N is the number to test
%
odd(N):- 1 is (N mod 2).


% Test Function
% Since we need the results printed to the console,
%  we need to explicitly print 'true' and 'false'
%  - This simply calls the odd rule, and explicitly
%    prints based on the boolean result
%
test(Num) :- catch((odd(Num) -> write('True') ; write('False')), 
        error(Err,_Context), (write('ERROR: '), write(Err))).

% getNum function to get passed in number from command argument list
%   - the command line arguments when program is run are: 
%    "/usr/local/bin/swipl -q -g main -t halt -f fileName.pl NumArg"
%    so we want the 9th argument ('NumArg'), returned as a number
%
getNum([_, _, _, _, _, _, _, _, NumAtom |_], Num) :- atom_number(NumAtom, Num).

% main function
%   - entry point for program
%   - will call test, passing in the given number
%
%   Usage: /usr/local/bin/swipl -q -g main -t halt -f fileName.pl 9
%
main :-
    current_prolog_flag(argv, Args), getNum(Args, Num), test(Num), nl.


