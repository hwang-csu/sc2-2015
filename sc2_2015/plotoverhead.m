function plotoverhead()
x1=[118 115 94];
x2=[17 14 8]; 
colormap summer
bar([x1;x2])
legend('DASF','TaintDroid','Android-JB')
%xlabel('The number of endorsing sensors')
ylabel('Milliseconds')
%axis([0,0,0,120])
grid
set(gca, 'XTickLabel', {'Activities'; 'Services'})
h = get(gca, 'xlabel');
set(h, 'FontSize', [16])
h = get(gca, 'ylabel');
set(h, 'FontSize', [16])
h = get(gca, 'title')
set(h, 'FontSize', [16])
set(gca, 'FontSize', [16])

