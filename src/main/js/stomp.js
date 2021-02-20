import webstomp from "webstomp-client";

export class SocketClient {
    /**
     * @param {string} address
     * @param {boolean} debug
     */
    constructor(address = "ws://localhost:8080", debug = false) {
        this.stompClient = webstomp.client(`${address}/socks`, {
            debug,
        });
        this.stompClient.connect(
            {},
            () => console.log(`Connected to ${address}`),
            (error) => {
                throw new Error(
                    `Failed to establish connection to "${address}": ${error.toString()}`
                );
            }
        );
    }

    /**
     * @param {string} topic
     *  @param {function} callback
     */
    listen(topic, callback) {
        this.stompClient.subscribe(`/topic/${topic}`, (response) => {
            callback(response);
            response.ack();
        });
    }

    /**
     * @param {string} endpoint
     * @param body
     */
    broadcast(endpoint, body) {
        this.stompClient.send(`/app/${endpoint}`, JSON.stringify(body));
    }
}
