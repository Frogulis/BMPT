# BMPT
render bitmap images using characters

### prerequisites
java compiler and runtime\
jopt-simple in the build directory\
24- or 32-bit BMP image

### building
clone or download the repository to a directory on your computer\
run the command `./build.sh` in a bash shell\
alternatively, run the commands therein manually

### running
the structure of a command to run the program is
`java -jar BMPT.jar [options] [filename]`

the options can consist of\
`-c [integer]` -- the size of the character set to use. currently 32 and 16 are valid.\
`-w [integer]` -- the maximum width (in characters). set this to something smaller or equal to half your terminal's width

neither of these options are required

