package utils

String str = args[0];
System.out.println("")
System.out.println("Using str => ${str}");
/*String fileText = new File(fileName).getText('UTF-8') */

String encoded = "${str}".bytes.encodeBase64().toString();
System.out.println("${encoded}")
