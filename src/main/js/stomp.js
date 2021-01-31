import webstomp from "webstomp-client";

const stompClient = webstomp.client("ws://localhost:8080/chat");
stompClient.connect({}, () => {
    console.log("connected");
    stompClient.subscribe("/topic/messages", (x) => {
        console.log(x);
        x.ack();
    });
});

window.sendit = () => {
    stompClient.send("/app/chat", JSON.stringify({ from: "me", text: "lmao" }));
};
