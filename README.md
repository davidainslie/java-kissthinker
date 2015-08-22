Java KISS Thinker
=================
Java source to accompany book. 

```bash
git clone https://github.com/davidainslie/java-kissthinker.git

mvn test
```

Release to Maven Central
------------------------
[Publishing from github to Maven Central](http://datumedge.blogspot.co.uk/2012/05/publishing-from-github-to-maven-central.html)

Cutting a release

To cut a release, issue mvn clean release:prepare.
Say your project is at version 0.1-SNAPSHOT.
Preparing a release will remove the -SNAPSHOT suffix, commit and tag the code on github, then update the project version to 0.2-SNAPSHOT.

If you need to undo the release, revert the changes with
git reset <hyphen><hyphen>hard HEAD~2
and delete the tag with
git tag -d redis-launcher-0.1
followed by
git push origin :refs/tags/redis-launcher-0.1.

Publishing to Sonatype

If all is well, you can publish the project artifacts to Sonatype by issuing
mvn release:perform
Log into Sonatype Nexus and find your artifacts under Staging Repositories
Close, then release the artifacts.
Sonatype have some good instructions on using their web interface.
You can automate these steps using the Maven repository management plugin, though I haven't tried it myself.
Add a comment to your JIRA ticket to say that you've promoted the release
The next time Sonatype synchronises (usually every few hours) your artifacts should be made available on Maven Central.