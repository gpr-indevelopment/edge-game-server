import React from "react";
import styles from "./VideoPlayer.module.css";
import { Button } from "antd";
import backendServer from "./backendServer";

function VideoPlayer() {

  return (
    <div className={styles.container}>
        <video id={"videoRtp"} width="640px" height="480px" onLoadedMetadata={(e ) => console.log(e)}/>
        <div className={styles.buttonContainer}>
          <Button
            type="primary"
            onClick={() => {
              const videoElement = document.getElementById("videoRtp");
              backendServer.startStream(videoElement);
            }}
          >
            Play
          </Button>
          <Button onClick={() => {
            console.log("Starting video!")
            const videoElement = document.getElementById("videoRtp");
            videoElement.play().catch((err) => {
              if (err.name === "NotAllowedError") {
                console.error("Browser doesn't allow playing video: " + err);
              } else {
                console.error("Error while playing video from stream: " + err);
              }
            });
          }}>Start video</Button>
          <Button type="danger" onClick={backendServer.sendInputLag}>Test InputLag</Button>
        </div>
      </div>
  );
}

export default VideoPlayer;
