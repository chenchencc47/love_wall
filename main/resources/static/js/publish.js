// API基础URL
const API_BASE_URL = 'http://localhost:8080/api/confessions';

// DOM元素
const confessionForm = document.getElementById('confessionForm');
const successToast = document.getElementById('successToast');
const errorToast = document.getElementById('errorToast');
const errorMessage = document.getElementById('errorMessage');

// 初始化
document.addEventListener('DOMContentLoaded', function() {
    // 表单提交事件
    confessionForm.addEventListener('submit', handleFormSubmit);

    // 添加爱心动画
    animateHearts();
});

// 处理表单提交
async function handleFormSubmit(event) {
    event.preventDefault();

    const confession = {
        fromWho: document.getElementById('fromWho').value.trim(),
        toWho: document.getElementById('toWho').value.trim(),
        content: document.getElementById('content').value.trim(),
        isAnonymous: document.getElementById('isAnonymous').checked
    };

    // 简单验证
    if (!confession.fromWho || !confession.toWho || !confession.content) {
        showError('请填写完整信息');
        return;
    }

    if (confession.content.length > 500) {
        showError('表白内容不能超过500字');
        return;
    }

    try {
        const response = await fetch(API_BASE_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(confession)
        });

        const result = await response.json();

        if (result.code === 200) {
            showSuccess();
            confessionForm.reset();
        } else {
            showError('发布失败：' + result.message);
        }
    } catch (error) {
        console.error('Error submitting confession:', error);
        showError('网络错误，请检查后端服务是否启动');
    }
}

// 显示成功提示
function showSuccess() {
    successToast.classList.add('show');
    setTimeout(() => {
        successToast.classList.remove('show');
    }, 5000);
}

// 显示错误提示
function showError(message) {
    errorMessage.textContent = message;
    errorToast.classList.add('show');
    setTimeout(() => {
        errorToast.classList.remove('show');
    }, 5000);
}

// 爱心动画
function animateHearts() {
    const heartsContainer = document.querySelector('.hearts-background');

    for (let i = 0; i < 15; i++) {
        setTimeout(() => {
            createHeart(heartsContainer);
        }, i * 300);
    }
}

function createHeart(container) {
    const heart = document.createElement('div');
    heart.innerHTML = '💖';
    heart.style.position = 'absolute';
    heart.style.fontSize = Math.random() * 20 + 10 + 'px';
    heart.style.left = Math.random() * 100 + 'vw';
    heart.style.top = '100vh';
    heart.style.opacity = '0.3';
    heart.style.animation = `floatHeart ${Math.random() * 10 + 10}s linear infinite`;

    container.appendChild(heart);

    // 清理
    setTimeout(() => {
        heart.remove();
    }, 20000);
}

// 添加浮动动画
const style = document.createElement('style');
style.textContent = `
    @keyframes floatHeart {
        0% {
            transform: translateY(0) rotate(0deg);
            opacity: 0.3;
        }
        50% {
            opacity: 0.6;
        }
        100% {
            transform: translateY(-100vh) rotate(360deg);
            opacity: 0;
        }
    }
`;
document.head.appendChild(style);