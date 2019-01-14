class SocketChannel {
    constructor(outputElementId = `output`, inputElementId = `message`) {
        this.output = document.getElementById(outputElementId);
        this.input = document.getElementById(inputElementId);
        this.input.form.addEventListener(`submit`, (evt) => {
            this.doSend(this.input.value);
            this.input.value = "";
            evt.preventDefault();
        }, true);
        this.webSocket = Object.assign(new WebSocket(SocketChannel.WS_URI), {
            onerror: (evt) => this.writeToScreen(`ERROR: ${evt.data}`),
            onmessage: (evt) => this.writeToScreen(`Message Received: ${evt.data}`),
            onopen: () => this.writeToScreen(`Connected to Endpoint!`),
        });
        addEventListener(`beforeunload`, this.webSocket.close
            .bind(this.webSocket));
    }
    writeToScreen(message) {
        const paragraph = document.createElement(`p`);
        paragraph.appendChild(document.createTextNode(message));
        this.output.appendChild(paragraph);
    }
    doSend(message) {
        this.webSocket.send(message);
        this.writeToScreen(`Message Sent: ${message}`);
    }
}
SocketChannel.WS_URI = `ws://localhost:8090/echo`;
//# sourceMappingURL=SocketChannel.js.map