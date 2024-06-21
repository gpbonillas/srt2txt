# SRT files To Text files

JavaFX application to convert YouTube subtitles (.srt) files to text (.txt) files.

The app load the content, then process it, deleting numbers and timestamps of each line within srt file. Finally, the text lines are display on textarea field.

## Format of .srt File
The YouTube subtitle files has the following format:

```srt
16 
00:00:36,960 --> 00:00:41,759
Hello World!
````

1. The first line is an integer number
2. Timestamp range
3. Subtitle line (text)
