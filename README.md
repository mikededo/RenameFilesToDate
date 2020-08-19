# RENAME FILES SCRIPT
The goal of this script is to automate the name changing of files to the date they were last modified.

## Example
We have a folder with the following files: 

| Name        | Last Modified |
| ----------- | ------------: |
| file01.txt  |    29-06-2000 |
| file02.txt  |    29-06-2000 |
| file03.txt  |    29-06-2000 |
| image01.jpg |    29-06-2000 |
| file01.txt  |    30-06-2000 |

After executing the small program, our files will look like:

| Name               | Last Modified |
| ------------------ | ------------: |
| 29-06-2000 (1).txt |    29-06-2000 |
| 29-06-2000 (2).txt |    29-06-2000 |
| 29-06-2000 (3).txt |    29-06-2000 |
| 29-06-2000.jpg     |    29-06-2000 |
| 30-06-2000.txt     |    30-06-2000 |

It changes the name keeping the extension to the _Last Modified Date_ of the file. If there are files with the same date and the same extension, it adds a counter to them with the orther they had with the other names.

## Usage
It has only been tested in Windows and it was programmed with `Java 14`. 
When the program has been started after compilation (compilation: `javac *.java`; run: `java Rename`) the folder navigation will pop in the console. Using the commands from the console you can navigate to the desired folder and once you are __INSIDE__ the folder you would like to change, use the `s` option.

> __Important Note__  
> The program will name change all the files from the folder including the files from the folders inside the choosen on