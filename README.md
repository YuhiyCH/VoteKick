# VoteKick
MC-VoteKick 投票踢人

插件简介：
他是一款通过玩家发起投票进行踢人的一个插件，可自定义倒计时，多少人才能发动投票，并且拥有权限的人不会被选中踢出的目标玩家

插件指令：

/vk - 查看 VoteKick 插件帮助

/vk help - 查看 VoteKick 插件帮助

/vk kick <玩家名字> - 踢出玩家

/vk yes - 投票同意

/vk no - 投票拒绝

/vk stop - 立即结束当前投票(不会踢出玩家)

/vk reload - 重载插件配置


插件权限：

votekick.vote - 允许投票权限(默认玩家拥有)

votekick.bypass - 不能被选中投票目标玩家

votekick.admin - 管理权限


插件变量：

（非插件本身变量需要使用到PlaceholderAPI）

%votekick_time% - 返回投票倒计时

%votekick_yes% - 返回当前投票同意票数

%votekick_no% - 返回当前投票拒绝票数


插件更新记录：

2.0

增加Gui踢出列表菜单

被踢出的玩家进行延迟进入

且发动投票内时间(可自定义)内不能再次发起投票

优化了一些代码


1.0

发布插件

