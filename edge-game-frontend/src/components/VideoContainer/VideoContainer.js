import React, { useEffect, useState } from "react";

function VideoContainer(props) {
  useEffect(() => {
    const videoElement = document.getElementById("videoRtp");
    if (props.isPlaying) {
      videoElement.play().catch((err) => {
        if (err.name === "NotAllowedError") {
          console.error("Browser doesn't allow playing video: " + err);
        } else {
          console.error("Error while playing video from stream: " + err);
        }
      });
    } else {
      videoElement.pause();
    }
  }, [props.isPlaying]);

  return (
    <video
      id={"videoRtp"}
      autoPlay
      playsInline
      width="640px"
      height="480px"
    ></video>
  );
}

export default VideoContainer;
