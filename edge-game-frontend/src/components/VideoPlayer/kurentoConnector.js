import kurentoUtils from "kurento-utils";
import backendWebsocket from "./backendWebsocket";

const postIceCandidate = (candidate) => {
  backendWebsocket.sendToServer(candidate);
}

const kurentoConnector = {
  init: (videoRtp) => {
    const options = {
      localVideo: null,
      remoteVideo: videoRtp,
      mediaConstraints: { audio: true, video: true },
      onicecandidate: (candidate) => postIceCandidate(candidate),
    };
    let webRtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerRecvonly(options, (err) => {
      if (err) {
        console.log("Error connecting to Kurento!", err);
        return;
      }
      console.log("Connected successfully to Kurento!");
    });
    backendWebsocket.addTopicListener((message) => {
      console.log("Raw message: " + message.body);
      let jsonMessage = JSON.parse(message.body);
      let payload = JSON.parse(jsonMessage.payload);
      console.log("Received listener message " + JSON.stringify(payload));
      webRtcPeer.addIceCandidate(payload.candidate, (err) => {
        if (err) {
          console.error("Error while handing add ice candidate " + err);
          return;
        }
        console.log("IceCandidate added!");
      });
    })
    return webRtcPeer;
  }
};

export default kurentoConnector;
