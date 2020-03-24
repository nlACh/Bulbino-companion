# Bulbino
A simple communication system for iot devices over a local network. Ultimate goal is to create a mesh network

This simple setup uses an esp8266 as a webserver, controlling a neopixel array __(could be anything instead)__. There's an accompanying android application to communicate with the server, and send or request data.

## Note:
The devices communicate via simple HTTP commands, and I think is safe for an isolated network, with no outside access.
So communications are via:
1. HTTP GET/POST
2. Simple webpage node access... like accessing *http://x.x.x.x/__(something)__*
3. As I approach towards a mesh network with more devices, I will aim for authentication access only.
4. Provision for **Master/Slave** communication in mesh.

The last 2 features are planned for future, as I get hold of more ESPs and other similar devices!
