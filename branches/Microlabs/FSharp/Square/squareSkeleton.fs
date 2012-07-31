// Mike Dusenberry
// F# Square Exercise
// 07/11/12
//
//<end!TopSection>
// Write a rule to square a given integer
//
// ex:
//  square 6; returns 36
//



//<end!MidSection>
// Test code
//  - Access the passed-in value (1st arg is the executable name), 
//    and cast as an int
//  - Call rule
//  - Print value
if args.Length >= 2 then // need a value passed in
    let value = int (System.Environment.GetCommandLineArgs().[1]);
    let ans = square value;
    printfn "%d" ans;
