function plotmsgover()
x1=[54.931, 73.242, 720.215, 8000, 49000, 468000];
colormap summer
bar([x1])
%legend('DASF','TaintDroid','Android-JB')
%xlabel('The number of endorsing sensors')
ylabel('Milliseconds')
%axis([0,0,0,120])
grid
xlabel('Size of Data')
set(gca, 'YScale','log')
set(gca, 'XTickLabel', {'1 KB'; '5 KB'; '100 KB'; '1 MB'; '5 MB'; '50 MB'})
h = get(gca, 'xlabel');
set(h, 'FontSize', [16])
h = get(gca, 'ylabel');
set(h, 'FontSize', [16])
h = get(gca, 'title')
set(h, 'FontSize', [16])
set(gca, 'FontSize', [16])

