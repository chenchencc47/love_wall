// API基础URL
const API_BASE_URL = 'http://localhost:8080/api/confessions';

// DOM元素
const wallContainer = document.getElementById('wall');
const refreshBtn = document.getElementById('refreshBtn');
const loadingElement = document.getElementById('loading');
const totalCountElement = document.getElementById('totalCount');
const todayCountElement = document.getElementById('todayCount');
const errorToast = document.getElementById('errorToast');
const errorMessage = document.getElementById('errorMessage');

// 初始化
document.addEventListener('DOMContentLoaded', function() {
    loadWall();

    // 刷新按钮事件
    refreshBtn.addEventListener('click', loadWall);

    // 添加爱心动画
    animateHearts();

    // 每30秒自动刷新
    setInterval(loadWall, 30000);
});

// 加载表白墙
async function loadWall() {
    showLoading();

    try {
        const response = await fetch(`${API_BASE_URL}/wall`);
        const result = await response.json();

        if (result.code === 200) {
            displayConfessions(result.data);
            updateStats(result.data);
        } else {
            showError('加载表白墙失败：' + result.message);
        }
    } catch (error) {
        console.error('Error loading wall:', error);
        showError('网络错误，请检查后端服务是否启动');
    } finally {
        hideLoading();
    }
}

// 显示表白便签
function displayConfessions(confessions) {
    wallContainer.innerHTML = '';

    if (!confessions || confessions.length === 0) {
        showEmptyState();
        return;
    }

    // 计算容器尺寸
    const containerWidth = wallContainer.clientWidth - 300;
    const containerHeight = wallContainer.clientHeight - 200;

    const usedPositions = [];

    confessions.forEach((confession, index) => {
        const position = findSuitablePosition(usedPositions, containerWidth, containerHeight);
        usedPositions.push(position);

        const note = createConfessionNote(confession, position);
        wallContainer.appendChild(note);

        // 添加入场动画
        setTimeout(() => {
            note.style.opacity = '1';
            note.style.transform = `rotate(${confession.rotation}deg)`;
        }, index * 50);
    });
}

// 找到合适的放置位置
function findSuitablePosition(usedPositions, maxWidth, maxHeight) {
    const noteWidth = 280;
    const noteHeight = 200;

    for (let attempt = 0; attempt < 100; attempt++) {
        const x = Math.random() * (maxWidth - noteWidth);
        const y = Math.random() * (maxHeight - noteHeight);

        const hasOverlap = usedPositions.some(pos => {
            return Math.abs(pos.x - x) < noteWidth * 0.8 &&
                Math.abs(pos.y - y) < noteHeight * 0.8;
        });

        if (!hasOverlap) {
            return { x, y };
        }
    }

    // 如果找不到合适位置，随机放置
    return {
        x: Math.random() * (maxWidth - noteWidth),
        y: Math.random() * (maxHeight - noteHeight)
    };
}

// 创建表白便签元素
function createConfessionNote(confession, position) {
    const note = document.createElement('div');
    note.className = 'confession-note';
    note.innerHTML = `
        <div class="from-to">${confession.fromWho} → ${confession.toWho}</div>
        <div class="content">${confession.content}</div>
        <div class="timestamp">${formatTime(confession.createdTime)}</div>
    `;

    // 应用样式
    applyNoteStyle(note, confession, position);

    // 初始状态（用于动画）
    note.style.opacity = '0';
    note.style.transition = 'opacity 0.5s ease, transform 0.3s ease';

    return note;
}

// 应用便签样式
function applyNoteStyle(note, confession, position) {
    note.style.fontFamily = confession.fontStyle;
    note.style.backgroundColor = confession.bgColor;
    note.style.color = confession.textColor;
    note.style.fontSize = confession.fontSize + 'px';
    note.style.transform = `rotate(${confession.rotation}deg)`;
    note.style.left = `${position.x}px`;
    note.style.top = `${position.y}px`;
    note.style.zIndex = confession.zIndex;
}

// 更新统计信息
function updateStats(confessions) {
    totalCountElement.textContent = confessions.length;

    // 计算今日新增（这里简单统计，实际应该由后端提供）
    const today = new Date().toDateString();
    const todayCount = confessions.filter(confession => {
        const confessionDate = new Date(confession.createdTime).toDateString();
        return confessionDate === today;
    }).length;

    todayCountElement.textContent = todayCount;
}

// 显示加载状态
function showLoading() {
    loadingElement.style.display = 'block';
    wallContainer.style.opacity = '0.5';
}

// 隐藏加载状态
function hideLoading() {
    loadingElement.style.display = 'none';
    wallContainer.style.opacity = '1';
}

// 显示空状态
function showEmptyState() {
    wallContainer.innerHTML = `
        <div class="empty-state">
            <h3>💝 还没有表白哦</h3>
            <p>快来发布第一条表白吧！</p>
            <a href="index.html" class="publish-btn" style="display: inline-block; margin-top: 1rem;">
                发布表白
            </a>
        </div>
    `;
}

// 显示错误提示
function showError(message) {
    errorMessage.textContent = message;
    errorToast.classList.add('show');
    setTimeout(() => {
        errorToast.classList.remove('show');
    }, 5000);
}

// 工具函数：格式化时间
function formatTime(dateTime) {
    const date = new Date(dateTime);
    const now = new Date();
    const diffMs = now - date;
    const diffMins = Math.floor(diffMs / 60000);
    const diffHours = Math.floor(diffMs / 3600000);
    const diffDays = Math.floor(diffMs / 86400000);

    if (diffMins < 1) return '刚刚';
    if (diffMins < 60) return `${diffMins}分钟前`;
    if (diffHours < 24) return `${diffHours}小时前`;
    if (diffDays < 7) return `${diffDays}天前`;

    return date.toLocaleDateString('zh-CN');
}

// 爱心动画（同发布页面）
function animateHearts() {
    const heartsContainer = document.querySelector('.hearts-background');

    for (let i = 0; i < 20; i++) {
        setTimeout(() => {
            createHeart(heartsContainer);
        }, i * 200);
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

    setTimeout(() => {
        heart.remove();
    }, 20000);
}