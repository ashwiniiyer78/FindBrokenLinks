#This is to find the broken links in webpage
echo -n "enter the url to test"
read url 
Echo "Your URL Entered : $url"
java -jar  /Users/A6004580/IdeaProjects/adweb/target/brokenLinks.jar $url

