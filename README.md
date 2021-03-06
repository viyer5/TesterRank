# TesterRank
ReadMe
One of the key challenges that we encountered when we began working on this tool was using the Major Mutation Framework. The documentation provided by the makers of the Major Mutation Framework was too high level for inexperienced researchers like us and their documentation significantly lacked details. In order to begin using our tool the first thing you'll have to do is to download the official documentation from this site -  (http://mutation-testing.org/doc/major.pdf). Next step is to download and install Java7 on your machine since the Major Mutation Framework is integrated with the Java7 compiler. If you've multiple Java versions installed on your Mac follow the instructions on this link to choose Java7 - (https://stackoverflow.com/questions/21964709/how-to-set-or-change-the-default-java-jdk-version-on-os-x).

Now that  Java7  has been installed you can clone our project in a folder location of your choice. It should have the following folders - bin, config, doc, example, lib and mml. Please note that inorder to kick start the Major Mutation Framework you'll have to update your environment and prepend Major’s bin directory to your execution path (PATH variable). For a Mac OS, the terminal command will be export PATH="/Users/vighneshiyer/Downloads/major/bin:$PATH"             
$Please note that, this path will be different for you based on where you have cloned your repository. I had cloned it in a folder called major in my Downloads folder and hence that terminal-command worked for me.
Once this step is done, run this command in your terminal "javac -version" without the quotes. The output should be "javac 1.7.0-Major-v1.3.4". If this is not your output then it means that the major framework is not ready to work yet. If you're not getting this ouput please take a look at the official documentation and follow their instructions and do some googling to get that output. We struggled a lot to get this working and hence have detailed the steps we followed, but if they don't work for you please contact us.

I am assuming that you've reached the point where javac -version  returns  "javac 1.7.0-Major-v1.3.4". Now the next step is to first check if everything is in place and is working. cd into example/ant and run the command "ant clean".

Now in the ant folder, cd into src/triangle and when you do "ls" you should be able to see SampleStack.java. Note that if you want to upload your own source file you will have to delete SampleStack.java and paste your file in this exact same folder. (We know it is annoying but thats how the major mutation framework has been configured)

Please go back to the ant folder and cd into test/triangle/test when you do "ls" you should be able to see TestSuite.java. Note that if you want to upload your own test suite file, you will have to name it TestSuite.java and copy that file in the exact same directory and the first line of your TestSuite.java should be "package triangle.test;". (Please note that, these instructions were provided no where in the documentation and we had to figure this all by ourselves, hence it will be beneficial for you to follow our instructions)

Go back to the ant folder again, and run the command ant -DmutOp="=all.mml.bin" compile. If everything is working properly the terminal  should say " [javac] #Generated Mutants: 53 (61 ms)". This command basically compiles your src file and generates mutants.

The next step is to run the command "ant compile.tests" without the quotes. This command essentially compiles your TestSuite.java file

The next step is to run the command "ant mutation.test" without the quotes. This command does all the magic and the Major Mutation Framework will generate a list of log files which our program is going to consume as inputs.

After running these three commands you must still be in the ant folder.

Now it is time to unleash TesterRank. If you do "ls" you'll be able to see a TesterRank.java file in the ant folder. And the real sauce lies in this file.

The next step is to run this program for that run the following command in your terminal - "javac -Xlint  TesterRank.java" without the quotes. It should not give you any errors if it does, go and fix them

The next step is to run the command "java TesterRank" and this will generate a ranking of all the test cases of your testing suite along with the two combinations.
