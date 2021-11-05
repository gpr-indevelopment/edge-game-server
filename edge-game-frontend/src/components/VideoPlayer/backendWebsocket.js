import SockJS from "sockjs-client";
import { Stomp } from "@stomp/stompjs";
import { config } from "../../constants";

var sock = new SockJS(`${config.url}/web-socket`);
var stompClient = Stomp.over(sock);
stompClient.connect({}, (frame) => console.log("Connected", frame));

let functions = {
    sendToServer: (message) => {
        stompClient.send("/server/message", {}, JSON.stringify(message));
    },
    addTopicListener: (listener) => {
        stompClient.subscribe('/topic/message', (message) => listener(message));
    }
};

export default functions;