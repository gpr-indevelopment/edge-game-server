import { config } from "../../constants";
import kurentoConnector from "./kurentoConnector";
import backendWebsocket from "./backendWebsocket";

const processSdpAnswer = (webRtcPeer, answer) => {
    webRtcPeer.processAnswer(answer.kmsSdpAnswer, (err) => {
        if (err) {
          console.error("Error while handling SDP answer from backend " + err);
          return;
        }
        console.log("Success while processing SDP answer from backend. Starting video.");
      });
}

const processSdpOffer = (webRtcPeer) => {
    webRtcPeer.generateOffer((err, sdpOffer) => {
        if (err) {
          console.error("Error generating SDP offer " + err);
          return;
        }
        fetch(config.url + "/start-stream", {
          method: "POST",
          body: sdpOffer
        })
          .then((res) => res.json())
          .then((res) => processSdpAnswer(webRtcPeer, res));
      });
}

let startStream = (videoElement) => {
  let webRtcPeer = kurentoConnector.init(videoElement);
  processSdpOffer(webRtcPeer);
}

let stopStream = (streamId) => {
  return fetch(`${config.url}/${streamId}/stop-stream`, {
    method: "POST",
  });
}

const backendServer = {
  startStream: (videoElement) => startStream(videoElement),
  stopStream: (streamId) => stopStream(streamId),
  sendInputLag: () => backendWebsocket.sendInputLag()
};
export default backendServer;
