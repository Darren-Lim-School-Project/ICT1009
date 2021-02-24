import webstomp from "webstomp-client";

const sleep = (ms) => new Promise((resolve) => setTimeout(resolve, ms));

export class SocketClient {
    /**
     * @param {string} address
     * @param {boolean} debug
     */
    constructor(address = "ws://localhost:8080", debug = false) {
        this.open = false;
        this.stompClient = webstomp.client(`${address}/socks`, {
            debug,
            heartbeat: { outgoing: 5000, incoming: 5000 },
        });

        this.stompClient.connect(
            {},
            () => {
                console.log(`Connected to ${address}`);
                this.open = true;
                setInterval(() => {
                    this.stompClient._wsSend("\x0A");
                    console.log(">>> PING");
                }, 20000);
            },
            (error) => {
                throw new Error(
                    `Failed to establish connection to "${address}": ${error.toString()}`
                );
            }
        );
    }

    /**
     *
     * @param {string} topic
     * @param {function} callback
     * @returns {Promise<Subscription>}
     */
    async listen(topic, callback) {
        if (!this.open) {
            await sleep(2 * 1000);
        }

        return this.stompClient.subscribe(`/topic/${topic}`, (response) => {
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
