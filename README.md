Spring 3.1 Example Project
==========================

This project stores code that is being built incrementally 
through my blog.  The blog is located at 
http://tresbackblog-tresback.rhcloud.com and dives into 
various topics dealing with Java, Spring, and ReST services.

The first features implemented use Java configuration of a 
Spring 3.1 project.  This allows us to remove XML configuration
code within the web.xml and Spring servlet config XML files
that previously drove our applications.  There should be 
plenty of comments directing how old XML values are 
now represented in the Java code.

The second implementation detail added gives bean controlling
power to the Spring application through a ReST interface. 
This power gives an administration user the ability to turn
beans on and off or change the implementation of a wired bean
during execution time, without the need for server restarts.

Finally, there are calls to my Spring Invoker project that 
provides a means for this application to make downstream
SOAP and ReST service calls without having to load in the 
client stub classes of those services, and without hard-
coding the interface code into the application.
