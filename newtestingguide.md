 You're using Windows PowerShell, so the commands need to be adapted for Windows. Here are the correct commands:

  🚀 Windows PowerShell Commands

  1. Create Directories (Windows)

  # Create target directories
  New-Item -ItemType Directory -Force -Path "target\classes"
  New-Item -ItemType Directory -Force -Path "target\test-classes"

  2. Compile Everything (Windows)

  # Compile all classes in dependency order (Windows paths)
  javac -cp "." -d target\classes src\main\java\com\community\communityApp\repository\Identifiable.java
  javac -cp "target\classes;." -d target\classes src\main\java\com\community\communityApp\repository\Repository.java
  javac -cp "target\classes;." -d target\classes src\main\java\com\community\communityApp\exception\*.java
  javac -cp "target\classes;." -d target\classes src\main\java\com\community\communityApp\model\*.java
  javac -cp "target\classes;." -d target\classes src\main\java\com\community\communityApp\repository\InMemoryRepository.java
  javac -cp "target\classes;." -d target\classes src\main\java\com\community\communityApp\util\*.java
  javac -cp "target\classes;." -d target\classes src\main\java\com\community\communityApp\service\*.java
  javac -cp "target\classes;." -d target\classes src\main\java\com\community\communityApp\CommunityAppApplication.java
  javac -cp "target\classes;." -d target\test-classes src\test\java\com\community\communityApp\TestRunner.java
  javac -cp "target\classes;." -d target\test-classes src\test\java\com\community\communityApp\InteractiveTest.java

  3. Run the Tests (Windows)

  🤖 Automated Unit Tests

  java -cp "target\test-classes;target\classes" com.community.communityApp.TestRunner

  🎮 Interactive Testing

  java -cp "target\test-classes;target\classes" com.community.communityApp.InteractiveTest

  📱 Full Application

  java -cp "target\classes" com.community.communityApp.CommunityAppApplication

  🔑 Key Differences for Windows:

  1. Path separators: Use \ instead of /
  2. Classpath separator: Use ; instead of :
  3. Directory creation: Use New-Item instead of mkdir -p

  📋 Quick Windows Commands:

  # 1. Create directories
  New-Item -ItemType Directory -Force -Path "target\classes"
  New-Item -ItemType Directory -Force -Path "target\test-classes"

  # 2. Compile everything (copy-paste this block)
  javac -cp "." -d target\classes src\main\java\com\community\communityApp\repository\Identifiable.java; javac -cp "target\classes;." -d target\classes
  src\main\java\com\community\communityApp\repository\Repository.java; javac -cp "target\classes;." -d target\classes src\main\java\com\community\communityApp\exception\*.java; javac -cp "target\classes;."
  -d target\classes src\main\java\com\community\communityApp\model\*.java; javac -cp "target\classes;." -d target\classes src\main\java\com\community\communityApp\repository\InMemoryRepository.java; javac
  -cp "target\classes;." -d target\classes src\main\java\com\community\communityApp\util\*.java; javac -cp "target\classes;." -d target\classes src\main\java\com\community\communityApp\service\*.java; javac
   -cp "target\classes;." -d target\classes src\main\java\com\community\communityApp\CommunityAppApplication.java; javac -cp "target\classes;." -d target\test-classes
  src\test\java\com\community\communityApp\TestRunner.java; javac -cp "target\classes;." -d target\test-classes src\test\java\com\community\communityApp\InteractiveTest.java

  # 3. Run automated tests
  java -cp "target\test-classes;target\classes" com.community.communityApp.TestRunner

  🎯 Try This Now:

  1. Create directories:
  New-Item -ItemType Directory -Force -Path "target\classes"
  New-Item -ItemType Directory -Force -Path "target\test-classes"

  2. Run the first compilation command:
  javac -cp "." -d target\classes src\main\java\com\community\communityApp\repository\Identifiable.java

  If that works without errors, continue with the next commands one by one!


  --------
    🚀 Try These Windows Commands Now:

  # 1. Create directories
  New-Item -ItemType Directory -Force -Path "target\classes"
  New-Item -ItemType Directory -Force -Path "target\test-classes"

  # 2. Compile utilities (should work now)
  javac -cp "target\classes;." -d target\classes src\main\java\com\community\communityApp\util\*.java

  # 3. If step 2 works, compile everything else
  javac -cp "." -d target\classes src\main\java\com\community\communityApp\repository\Identifiable.java
  javac -cp "target\classes;." -d target\classes src\main\java\com\community\communityApp\repository\Repository.java
  javac -cp "target\classes;." -d target\classes src\main\java\com\community\communityApp\exception\*.java
  javac -cp "target\classes;." -d target\classes src\main\java\com\community\communityApp\model\*.java
  javac -cp "target\classes;." -d target\classes src\main\java\com\community\communityApp\repository\InMemoryRepository.java
  javac -cp "target\classes;." -d target\classes src\main\java\com\community\communityApp\service\*.java
  javac -cp "target\classes;." -d target\classes src\main\java\com\community\communityApp\CommunityAppApplication.java
  javac -cp "target\classes;." -d target\test-classes src\test\java\com\community\communityApp\TestRunner.java
  javac -cp "target\classes;." -d target\test-classes src\test\java\com\community\communityApp\InteractiveTest.java

  # 4. Run the tests
  java -cp "target\test-classes;target\classes" com.community.communityApp.TestRunner
