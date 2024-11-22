<template>
  <div
    v-if="isOpen"
    class="chat-popup"
    :style="{
      left: position.x + 'px',
      top: position.y + 'px',
      width: size.width + 'px',
      height: size.height + 'px',
    }"
    @mousedown="startDrag"
  >
    <div class="chat-window">
      <div class="chat-header" @mousedown="startDrag">
        <h3>{{ $t('chat.title') }}</h3>
        <div class="header-buttons">
          <button @click="toggleSize" class="size-btn">
            {{ isExpanded ? '−' : '＋' }}
          </button>
          <button @click="closeChat" class="close-btn">
            {{ $t('chat.close') }}
          </button>
        </div>
      </div>
      <div class="chat-messages">
        <div
          v-for="(msg, index) in messages"
          :key="index"
          :class="['message', msg.type]"
        >
          {{ msg.content }}
        </div>
      </div>
      <div class="chat-input">
        <input
          v-model="userInput"
          @keyup.enter="sendMessage"
          :placeholder="$t('chat.placeholder')"
        />
        <button @click="sendMessage" class="send-btn">
          {{ $t('chat.send') }}
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import { useI18n } from 'vue-i18n';

export default {
  name: 'ChatPopup',
  setup() {
    const { t } = useI18n();
    return { t };
  },
  data() {
    const user = JSON.parse(localStorage.getItem('user'));
    const userId = user ? user.userId : 'guest';
    const storage = user ? localStorage : sessionStorage;
    const chatOpen = storage.getItem(`chatOpen_${userId}`) === 'true';

    return {
      isOpen: chatOpen,
      userInput: '',
      messages: [],
      position: {
        x: window.innerWidth / 2 - 175,
        y: window.innerHeight / 2 - 250,
      },
      size: {
        width: 350,
        height: 500,
      },
      isExpanded: false,
      baseSize: {
        width: 350,
        height: 500,
      },
      expandedSize: {
        width: 500,
        height: 714,
      },
      isDragging: false,
      dragOffset: { x: 0, y: 0 },
    };
  },
  mounted() {
    const user = JSON.parse(localStorage.getItem('user'));
    const userId = user ? user.userId : 'guest';
    const storage = user ? localStorage : sessionStorage;
    const savedMessages = storage.getItem(`chatMessages_${userId}`);
    if (savedMessages) {
      this.messages = JSON.parse(savedMessages);
    }
  },
  watch: {
    messages: {
      deep: true,
      handler(newMessages) {
        const user = JSON.parse(localStorage.getItem('user'));
        const userId = user ? user.userId : 'guest';
        const storage = user ? localStorage : sessionStorage;
        storage.setItem(`chatMessages_${userId}`, JSON.stringify(newMessages));
      },
    },
    isOpen(newValue) {
      const user = JSON.parse(localStorage.getItem('user'));
      const userId = user ? user.userId : 'guest';
      const storage = user ? localStorage : sessionStorage;
      storage.setItem(`chatOpen_${userId}`, newValue);
    },
  },
  methods: {
    async sendMessage() {
      if (!this.userInput.trim()) return;

      try {
        const user = JSON.parse(localStorage.getItem('user'));
        const userId = user ? user.userId : 'guest';

        // 사용자 메시지 추가
        this.messages.push({
          type: 'user',
          content: this.userInput,
        });

        const response = await fetch(
          'http://54.180.119.166:8080/api/chat/send',
          {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify({
              message: this.userInput,
              userId: userId,
            }),
          }
        );

        const botResponse = await response.json();

        // 봇 응답 추가
        this.messages.push({
          type: 'bot',
          content: botResponse.message,
        });
      } catch (error) {
        console.error(this.$t('chat.error'), error);
      }

      this.userInput = '';
    },
    clearChat() {
      const user = JSON.parse(localStorage.getItem('user'));
      const userId = user ? user.userId : 'guest';
      const storage = user ? localStorage : sessionStorage;
      this.messages = [];
      storage.removeItem(`chatMessages_${userId}`);
    },
    closeChat() {
      this.isOpen = false;
      const user = JSON.parse(localStorage.getItem('user'));
      const userId = user ? user.userId : 'guest';
      const storage = user ? localStorage : sessionStorage;
      storage.setItem(`chatOpen_${userId}`, false);
    },
    toggleSize() {
      this.isExpanded = !this.isExpanded;
      this.size = this.isExpanded ? this.expandedSize : this.baseSize;

      const widthDiff = (this.expandedSize.width - this.baseSize.width) / 2;
      const heightDiff = (this.expandedSize.height - this.baseSize.height) / 2;

      if (this.isExpanded) {
        this.position.x -= widthDiff;
        this.position.y -= heightDiff;
      } else {
        this.position.x += widthDiff;
        this.position.y += heightDiff;
      }
    },
    startDrag(event) {
      if (
        event.target.classList.contains('close-btn') ||
        event.target.tagName === 'INPUT' ||
        event.target.tagName === 'BUTTON'
      )
        return;

      this.isDragging = true;
      this.dragOffset = {
        x: event.clientX - this.position.x,
        y: event.clientY - this.position.y,
      };
      document.addEventListener('mousemove', this.drag);
      document.addEventListener('mouseup', this.stopDrag);
    },
    drag(event) {
      if (!this.isDragging) return;
      this.position = {
        x: event.clientX - this.dragOffset.x,
        y: event.clientY - this.dragOffset.y,
      };
    },
    stopDrag() {
      this.isDragging = false;
      document.removeEventListener('mousemove', this.drag);
      document.removeEventListener('mouseup', this.stopDrag);
    },
  },
};
</script>

<style scoped>
/* 기존 스타일 유지 */
.chat-popup {
  position: absolute;
  z-index: 1000;
}

.chat-window {
  width: 100%;
  height: 100%;
  background: white;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  position: relative;
}

.chat-header {
  padding: 15px;
  background: #00857e;
  color: white;
  border-radius: 10px 10px 0 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
  cursor: move;
}

.header-buttons {
  display: flex;
  gap: 10px;
  align-items: center;
}

.size-btn,
.close-btn {
  background: none;
  border: none;
  color: white;
  font-size: 20px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 24px;
  height: 24px;
  padding: 0;
}

.size-btn:hover,
.close-btn:hover {
  background: rgba(255, 255, 255, 0.1);
  border-radius: 4px;
}

.chat-messages {
  flex-grow: 1;
  padding: 15px;
  overflow-y: auto;
}

.message {
  margin: 10px 0;
  padding: 10px;
  border-radius: 10px;
  max-width: 80%;
}

.message.user {
  background: #e3f2fd;
  margin-left: auto;
}

.message.bot {
  background: #f5f5f5;
  margin-right: auto;
}

.chat-input {
  padding: 15px;
  border-top: 1px solid #eee;
  display: flex;
  gap: 10px;
}

.chat-input input {
  flex-grow: 1;
  padding: 8px;
  border: 1px solid #f7efef;
  border-radius: 5px;
  outline: none;
}

.send-btn {
  padding: 8px 15px;
  background: #00857e;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.send-btn:hover {
  background: #07d1c7;
}
</style>
