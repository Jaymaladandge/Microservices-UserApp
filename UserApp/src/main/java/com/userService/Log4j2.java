package com.userService;

public class Log4j2 {

	
/*



I believe you are on right track in terms of using a separate thread pool for logging. In lot of products you will see the 
asynchronous logging feature. Logs are accumulated and pushed to log files using a separate thread than the request thread.
 Especially in prodcution environments, where are millions of incoming request and your response time need to be less than 
 few seconds. You cannot afford anything such as logging to slow down the system. So the approach used is to add logs in 
 a memory buffer and push them asynchronously in reasonably sized chunks.
A word of caution while using thread pool for logging As multiple threads will be working on the log file(s) and on 
a memory log buffer, you need to be careful about the logging. You need to add logs in a FIFO kind of a buffer to be sure that
 logs are printed in the log files sorted by time stamp. Also make sure the file access is synchronized 
 and you don't run into situation where log file is all upside down or messed up.




In pom.xml
Excluding Default Logging Dependencies: By excluding logback-classic and spring-boot-starter-logging, you are removing Logback, the default logging framework provided by Spring Boot, to avoid conflicts with Log4j2.
Adding Log4j2 Dependencies: By adding log4j-api and log4j-core, you are including Log4j2 as the logging framework for your Spring Boot application.



# Application logger configuration 
logger.app.name = com.userService
logger.app.level = debug
logger.app.additivity = false
logger.app.appenderRefs = rolling
logger.app.appenderRef.rolling.ref = RollingFile


logger.app.name = com.userService

logger.app.name: Defines the name of the logger.
com.userService: The logger is associated with the com.userService package or class. This means that logging statements within the com.userService package or class will be handled by this logger.



logger.app.level = debug

logger.app.level: Specifies the logging level for the logger.
debug: Sets the logging level to DEBUG. This means that all messages at the DEBUG level and above (DEBUG, INFO, WARN, ERROR) will be logged.



logger.app.additivity = false

logger.app.additivity: Specifies whether the logger should pass its log messages to its parent logger.
false: Disables additivity, meaning that log messages handled by this logger will not be passed to parent loggers. This is useful if you want to prevent duplicate log messages.



logger.app.appenderRefs = rolling

logger.app.appenderRefs: Lists the appenders that this logger should use.
rolling: Refers to an appender named rolling. This means that log messages will be sent to the rolling appender.



logger.app.appenderRef.rolling.ref = RollingFile

logger.app.appenderRef.rolling.ref: Specifies the appender reference for the rolling appender.
RollingFile: Refers to an appender configuration named RollingFile. This means that the rolling appender is configured to use the RollingFile appender, which typically writes log messages to a file and manages file rolling based on size or time.


Putting it all together, these properties define a logger for the com.userService package or class, set to log messages at the DEBUG level, with additivity disabled to prevent duplicate messages in parent loggers. The logger uses an appender named rolling, which is configured to use the RollingFile appender for handling log messages.












To disable unwanted logs and print only Spring Boot application-specific logs, you can configure the logging level in the application.properties or application.yml file. Here's how you can achieve this:


1)Set logging level for specific packages:

logging.level.org.springframework=ERROR
logging.level.com.yourpackage=DEBUG

This configuration will set the Spring framework logs to ERROR level and your application's logs to DEBUG level.



2)Disable specific loggers:

logging.level.org.hibernate=OFF
logging.level.org.springframework.web=OFF

This will turn off logging for Hibernate and Spring Web packages.



3)Set the root logging level:

logging.level.root=WARN

This sets the root logger to WARN level, which means only warnings and errors will be logged globally, except for packages you have explicitly configured.




# Suppress Spring Logging
#logger.spring.name = org.springframework
#logger.spring.level = off
#logger.spring.additivity = false
#logger.spring.appenderRefs = console, rolling
#logger.spring.appenderRef.console.ref = Console
#logger.spring.appenderRef.rolling.ref = RollingFile
#
#logger.springboot.name = org.springframework.boot
#logger.springboot.level = off
#logger.springboot.additivity = false
#logger.springboot.appenderRefs = console, rolling
#logger.springboot.appenderRef.console.ref = Console
#logger.springboot.appenderRef.rolling.ref = RollingFile



logger.spring.name = org.springframework

logger.spring.name: Defines the name of the logger.
org.springframework: The logger is associated with the org.springframework package. This means that logging statements within the Spring framework package will be handled by this logger.



logger.spring.level = off

logger.spring.level: Specifies the logging level for the logger.
off: Disables logging for the org.springframework package entirely. No log messages from this package will be recorded.



logger.spring.additivity = false

logger.spring.additivity: Specifies whether the logger should pass its log messages to its parent logger.
false: Disables additivity, meaning that log messages handled by this logger will not be passed to parent loggers. This prevents duplicate log messages from being recorded by parent loggers.




logger.spring.appenderRefs = console, rolling

logger.spring.appenderRefs: Lists the appenders that this logger should use.
console, rolling: Refers to appenders named console and rolling. This means that log messages will be sent to both the console and rolling appenders.




logger.spring.appenderRef.console.ref = Console

logger.spring.appenderRef.console.ref: Specifies the appender reference for the console appender.
Console: Refers to an appender configuration named Console. This means that the console appender is configured to use the Console appender, which typically writes log messages to the console.




logger.spring.appenderRef.rolling.ref = RollingFile

logger.spring.appenderRef.rolling.ref: Specifies the appender reference for the rolling appender.
RollingFile: Refers to an appender configuration named RollingFile. This means that the rolling appender is configured to use the RollingFile appender, which typically writes log messages to a file and manages file rolling based on size or time.


Putting it all together, these properties define a logger for the org.springframework package, set to disable logging entirely (level = off), with additivity disabled to prevent duplicate messages in parent loggers. The logger uses two appenders: console (which writes log messages to the console) and rolling (which writes log messages to a rolling file).




# Root logger level and appender references
rootLogger.level = debug
rootLogger.appenderRefs = rolling
rootLogger.appenderRef.rolling.ref = RollingFile

# RollingFile appender configuration
appender.rolling.type = RollingFile
appender.rolling.name = RollingFile
appender.rolling.fileName = D://Data//logs//app.log
appender.rolling.filePattern = D://Data//logs//app-%d{yyyy-MM-dd}-%i.log.gz
appender.rolling.layout.type = PatternLayout
appender.rolling.layout.pattern = %d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n

# Policies for rolling over log files
appender.rolling.policies.type = Policies
appender.rolling.policies.size.type = SizeBasedTriggeringPolicy
appender.rolling.policies.size.size = 100MB
appender.rolling.strategy.type = DefaultRolloverStrategy
appender.rolling.strategy.max = 10



rootLogger.level = debug

rootLogger.level: Specifies the logging level for the root logger.
debug: Sets the root logger to the DEBUG level, meaning it will capture all log messages at the DEBUG level and above (DEBUG, INFO, WARN, ERROR).



rootLogger.appenderRefs = rolling

rootLogger.appenderRefs: Lists the appenders that the root logger should use.
rolling: Refers to an appender named rolling.



rootLogger.appenderRef.rolling.ref = RollingFile

rootLogger.appenderRef.rolling.ref: Specifies the appender reference for the rolling appender.
RollingFile: Refers to an appender configuration named RollingFile.



appender.rolling.type = RollingFile

appender.rolling.type: Defines the type of the appender.
RollingFile: Specifies that the appender is a rolling file appender, which means it will manage log files by rolling them over based on certain policies.



appender.rolling.name = RollingFile

appender.rolling.name: The name of the appender.
RollingFile: The name assigned to this appender configuration.



appender.rolling.fileName = D://Data//logs//app.log

appender.rolling.fileName: Specifies the file name for the primary log file.
D://Data//logs//app.log: The log file will be written to this location.



appender.rolling.filePattern = D://Data//logs//app-%d{yyyy-MM-dd}-%i.log.gz

appender.rolling.filePattern: Specifies the pattern for rolled-over log files.
D://Data//logs//app-%d{yyyy-MM-dd}-%i.log.gz: Rolled-over log files will follow this naming pattern, including date (%d{yyyy-MM-dd}) and an index (%i), and will be compressed (.gz).



appender.rolling.layout.type = PatternLayout

appender.rolling.layout.type: Defines the type of layout used by the appender.
PatternLayout: Specifies that the log messages will be formatted according to a pattern.



appender.rolling.layout.pattern = %d{yyyy-MM-dd HH:mm
} %-5p %c{1}:%L - %m%n

appender.rolling.layout.pattern: The pattern for formatting log messages.
%d{yyyy-MM-dd HH:mm
} %-5p %c{1}:%L - %m%n: This pattern includes the date, time, log level, logger name, line number, and the message.



appender.rolling.policies.type = Policies

appender.rolling.policies.type: Defines the type of policies used for rolling over log files.
Policies: A container for multiple rolling policies.



appender.rolling.policies.size.type = SizeBasedTriggeringPolicy

appender.rolling.policies.size.type: Specifies a size-based rolling policy.
SizeBasedTriggeringPolicy: The log file will roll over based on its size.



appender.rolling.policies.size.size = 100MB
	
appender.rolling.policies.size.size: Sets the size threshold for rolling over the log file.
100MB: The log file will roll over when it reaches 100 megabytes.



appender.rolling.strategy.type = DefaultRolloverStrategy

appender.rolling.strategy.type: Defines the rollover strategy.
DefaultRolloverStrategy: Uses the default strategy for managing rolled-over log files.



appender.rolling.strategy.max = 10

appender.rolling.strategy.max: Sets the maximum number of rolled-over log files to keep.
10: The system will retain up to 10 rolled-over log files, and older files will be deleted.



This configuration sets up the root logger to log messages at the DEBUG level and send them to a rolling file appender named RollingFile. The RollingFile appender writes log messages to D://Data//logs//app.log and rolls over the log file when it reaches 100MB. Rolled-over files are named according to a pattern and compressed. The system retains up to 10 rolled-over log files. The log messages are formatted to include the date, time, log level, logger name, line number, and the message.

















*/
	
}
