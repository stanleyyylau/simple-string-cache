# Simple String Cache

SSC(Simple String Cache) is a single node in-memory string-based key-value storage micro service. if you're looking for a simpler Redis alternative, This could be it.

![Simple String Cache](https://tva1.sinaimg.cn/large/008eGmZEgy1gmqr4iclp6j313y0ml768.jpg)

## Usage guide

+ Run as a Springboot Application: SSC include a Springboot App demo with Swagger UI, if you want out-of-the-box usage, just kick start the SpringBoot app.
+ Build your own Application leveraging the core: SSC follows two tier design where you can swap the web layer with any Java-based technology you prefer.

## Feature highlights

+ High concurrent requests supported
+ Ultra fast GET/PUT operation with average-case complexity less than On
+ High volume data supported with auto memory clean-up algorithm
+ Optimised CPU usage with both active and passive cache expire strategies

## Known issues and next steps

+ Performance might suffer under high concurrent requests due to `synchronized` lock. this will be fixed in later versions (high priority)
+ Java's `totalMemory()` method don't return correct value sometimes. later version will support multiple memory clean-up strategies (high priority) 
+ Optimize sorted linked list implementation to be more generic (low priority)
+ Include better scheduling for active cache removal (low priority)
+ Use AOP to add performance log and include cache monitoring UI in Web layer (low priority)