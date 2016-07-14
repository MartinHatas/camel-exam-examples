# camel-exam-examples

This project contains examples as my preparation for [EX421 Red Hat Certificate of Expertise in Camel Development exam] (https://www.redhat.com/en/services/training/ex421-red-hat-certificate-expertise-camel-development-exam)

## Exam format
This exam is a performance-based evaluation of the candidate's Camel development skills and knowledge. Candidates perform tasks associated with the development and maintenance of enterprise intragration patterns using Red Hat JBoss Fuse. So ability to work and deploy in JBoss Fuse is must as well as ability to code in Red Hat JBoss Developer Studio.

* [Getting started with JBoss Fuse development](https://access.redhat.com/documentation/en-US/Red_Hat_JBoss_Fuse/6.2/html/Getting_Started/)
* [Getting started with JBoss Developer Studio and JBoss Fuse] (https://www.jboss.org/products/fuse/get-started/)

## Skills needed
An enterprise application developer who has earned the Red Hat Certificate of Expertise in Camel Development is able to:

* Create and maintain Camel routes
* Consume and produce files of varying formats
* Work with databases and the Java™ Persistence API (JPA) Camel component
* Understand and use different enterprise integration patterns
* Configure dynamic routing of messages

## Topics that can show up in exam
* Create and maintain Camel routes
  * Use the Java™ language
  * Use the CamelContext XML
* Exchange messages
  * Body
  * In/out
  * Headers
  * Attachments
* Consume and produce files
  * CSV format [Examples](https://github.com/MartinHatas/camel-exam-examples/tree/master/csv/src/test/java/cz/hatoff/camel/examples)
  * XML format [Examples](https://github.com/MartinHatas/camel-exam-examples/tree/master/xml/src/test/java/cz/hatoff/camel/examples/xml/xstream)
* Work with databases and the Camel JPA component
* Understand transactional routes
* Use EIPs
  * Content-based routing
  * Wire Tap
  * Splitter
  * Aggregator
  * RecipientList
* Use Camel components
  * File2
  * FTP
  * JPA2
  * JMS
  * Direct
* Use Camel Test
  * Mock endpoints
  * JUnit 4 integration and CamelTest APIs
* Dynamically route messages
  * Use conditional routes
  * Use data-driven routes
* Handle exceptions
  * Catch and handle exceptions
  * Use the dead-letter queue
